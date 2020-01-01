package com.wenjun.seckill.controller;

import com.wenjun.seckill.enums.EmBusinessError;
import com.wenjun.seckill.error.BusinessException;
import com.wenjun.seckill.mq.MqProducer;
import com.wenjun.seckill.response.CommonReturnType;
import com.wenjun.seckill.service.ItemService;
import com.wenjun.seckill.service.OrderService;
import com.wenjun.seckill.service.PromoService;
import com.wenjun.seckill.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.*;

/**
 * @Author: wenjun
 * @Date: 2019/12/22 0:08
 */
@RestController
@RequestMapping("/order")
@CrossOrigin(allowedHeaders = "*",allowCredentials = "true")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PromoService promoService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private MqProducer mqProducer;

    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(20);
    }

    //生成秒杀令牌
    @PostMapping(value = "/generatetoken")
    public CommonReturnType createOrder(@RequestParam(name = "itemId") Integer itemId,
                                        @RequestParam(name = "promoId") Integer promoId) throws BusinessException {
        //根据token获取用户信息
        String token = httpServletRequest.getParameterMap().get("token")[0];
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户还未登录,不能下单");
        }
        //获取用户登录信息
        //UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get("token_" + token);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户登录已过期,请重新登录");
        }
        //若为非秒杀商品
        if (promoId == null) {
            return CommonReturnType.create(null);
        }
        //获取秒杀令牌
        String promoToken = promoService.generatePromoToken(promoId,userModel.getId(),itemId);
        if (promoToken == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"生成令牌失败");
        }
        return CommonReturnType.create(promoToken);
    }

    //封装下单请求
    @PostMapping(value = "/createorder")
    public CommonReturnType createOrder(@RequestParam(name = "itemId") Integer itemId,
                                        @RequestParam(name = "amount") Integer amount,
                                        @RequestParam(name = "promoId", required = false) Integer promoId,
                                        @RequestParam(name = "promoToken", required = false) String promoToken) throws BusinessException {
        //判断用户是否登录
//        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");//Session方案
//        if (isLogin == null || !isLogin) {
//            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户还未登录,不能下单");
//        }
        String token = httpServletRequest.getParameterMap().get("token")[0];
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户还未登录,不能下单");
        }
        //获取用户登录信息
        //UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get("token_" + token);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户登录已过期,请重新登录");
        }

        //校验秒杀令牌
        if (promoId != null) {
            String inRedisPromoToken = (String) redisTemplate.opsForValue().get("promo_token_" + promoId + "_userId_" + userModel.getId() + "_itemId_" + itemId);
            if (inRedisPromoToken == null || !StringUtils.equals(promoToken,inRedisPromoToken)) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"秒杀令牌校验失败");
            }
        }

        //判断库存是否已售罄，若对应售罄的key存在，则直接返回下单失败（已前置到PromoService的generatePromoToken方法中）
//        if (redisTemplate.hasKey("promo_item_stock_invalid_" + itemId)) {
//            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
//        }

        //同步调用线程池的submit方法
        //拥塞窗口为20的等待队列，用来队列化泄洪
        Future<Object> future = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                //加入库存流水init状态
                String stockLogId = itemService.initStockLog(itemId,amount);

                //再去完成对应的下单事务型消息机制
                //OrderModel orderModel = orderService.createOrder(userModel.getId(),itemId,amount,promoId);
                //使用RocketMQ事务型消息下单
                if (!mqProducer.transactionAsyncReduceStock(userModel.getId(),itemId,amount,promoId,stockLogId)) {
                    throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"下单失败");
                }
                return null;
            }
        });

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {//线程执行出现问题
            e.printStackTrace();
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
        }

        return CommonReturnType.create("下单成功");
    }
}
