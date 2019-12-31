package com.wenjun.seckill.service;

import com.wenjun.seckill.error.BusinessException;
import com.wenjun.seckill.service.model.OrderModel;

/**
 * @Author: wenjun
 * @Date: 2019/12/21 20:32
 */
public interface OrderService {
    //1.通过前端url上传过来秒杀活动id，然后下单接口内校验对应id是否属于对应商品且活动已开始(选择√)
    //2.直接在下单接口内判断对应的商品是否存在秒杀活动，若存在进行中的则以秒杀价格下单(普通商品不应判断)
    OrderModel createOrder(Integer userId,Integer itemId,Integer amount, Integer promoId, String stockLogId) throws BusinessException;
}
