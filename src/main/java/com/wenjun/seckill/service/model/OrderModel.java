package com.wenjun.seckill.service.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: wenjun
 * @Date: 2019/12/21 18:05
 */
@Data
public class OrderModel {
    //交易号
    private String id;
    private Integer userId;
    private BigDecimal itemPrice;
    private Integer itemId;
    private Integer amount;
    private BigDecimal orderPrice;
}
