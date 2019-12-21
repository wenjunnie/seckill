package com.wenjun.seckill.service.impl;

import com.wenjun.seckill.dao.OrderDOMapper;
import com.wenjun.seckill.dao.SequenceDOMapper;
import com.wenjun.seckill.dataobject.OrderDO;
import com.wenjun.seckill.dataobject.SequenceDO;
import com.wenjun.seckill.enums.EmBusinessError;
import com.wenjun.seckill.error.BusinessException;
import com.wenjun.seckill.service.ItemService;
import com.wenjun.seckill.service.OrderService;
import com.wenjun.seckill.service.UserService;
import com.wenjun.seckill.service.model.ItemModel;
import com.wenjun.seckill.service.model.OrderModel;
import com.wenjun.seckill.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: wenjun
 * @Date: 2019/12/21 20:32
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //校验下单状态
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不正确");
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");
        }
        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"数量信息不正确");
        }
        //落单减库存(√)OR支付减库存
        boolean result = itemService.decreaseStock(itemId,amount);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(BigDecimal.valueOf(amount)));
        //生成交易流水号，订单号
        orderModel.setId(generateOrderNo());
        //写入数据库
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
        //商品销量增加
        itemService.increaseSales(itemId,amount);
        //返回前端
        return orderModel;
    }

    //生成订单号，每次生成订单都开启新事务，因此createOrder回滚了此处并不会回滚
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateOrderNo() {
        //订单号16位
        StringBuilder builder = new StringBuilder();
        //前8位时间信息
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        builder.append(nowDate);
        //中间6位自增序列
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 6-sequenceStr.length(); i++) {
            builder.append(0);
        }
        builder.append(sequenceStr);
        //最后两位为分库分表位（暂时写死）
        builder.append("00");
        return builder.toString();
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDO;
    }
}
