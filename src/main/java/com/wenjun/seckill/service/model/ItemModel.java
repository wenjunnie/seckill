package com.wenjun.seckill.service.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: wenjun
 * @Date: 2019/12/20 14:43
 */
@Data
public class ItemModel {
    private Integer id;
    @NotBlank(message = "商品名称不能为空")
    private String title;
    @NotNull(message = "商品价格不能为空")
    @Min(value = 0,message = "商品价格必须大于0")
    private BigDecimal price;
    @NotNull(message = "商品库存不能为空")
    private Integer stock;
    @NotBlank(message = "商品描述不能为空")
    private String description;
    private Integer sales;
    private String imgUrl;
}
