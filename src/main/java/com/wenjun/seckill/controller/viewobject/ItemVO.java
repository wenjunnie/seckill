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
}
