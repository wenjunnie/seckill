package com.wenjun.seckill.service.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @Author: wenjun
 * @Date: 2019/12/22 0:53
 */
@Data
public class PromoModel {
    private Integer id;
    //秒杀活动名称
    private String promoName;
    //秒杀活动的开始时间
    private DateTime startDate;
    //秒杀活动的适用商品
    private Integer itemId;
    //秒杀活动的商品价格
    private BigDecimal promoPrice;
}
