package com.wenjun.seckill.controller.viewobject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: wenjun
 * @Date: 2019/12/21 16:16
 */
@Data
public class ItemVO {
    private Integer id;
    private String title;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private Integer sales;
    private String imgUrl;
    //记录商品是否在秒杀活动中，以及对应状态，0表示无活动，1表示待开始，2表示进行中
    private Integer promoStatus;
    //秒杀活动价格
    private BigDecimal promoPrice;
    //秒杀活动ID
    private Integer promoId;
    //秒杀活动开始时间
    private String startDate;
}
