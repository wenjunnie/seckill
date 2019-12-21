package com.wenjun.seckill.service;

import com.wenjun.seckill.error.BusinessException;
import com.wenjun.seckill.service.model.OrderModel;

/**
 * @Author: wenjun
 * @Date: 2019/12/21 20:32
 */
public interface OrderService {
    OrderModel createOrder(Integer userId,Integer itemId,Integer amount) throws BusinessException;
}
