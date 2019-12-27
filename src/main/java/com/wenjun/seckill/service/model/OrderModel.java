package com.wenjun.seckill.service.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: wenjun
 * @Date: 2019/12/21 18:05
 */
@Data
public class OrderModel implements Serializable {
    //交易号
    private String id;
    private Integer userId;
    //若非空，则表示是以秒杀商品方式下单
    private Integer promoId;
    //若promoId非空，则表示秒杀商品价格，否则正常价格
    private BigDecimal itemPrice;
    private Integer itemId;
    private Integer amount;
    //若promoId非空，则表示秒杀商品价格，否则正常价格
    private BigDecimal orderPrice;
}
