package com.wenjun.seckill.service;

import com.wenjun.seckill.service.model.PromoModel;

/**
 * @Author: wenjun
 * @Date: 2019/12/22 11:50
 */
public interface PromoService {
    //根据itemId获取即将开始或正在进行的秒杀活动
    PromoModel getPromoByItemId(Integer itemId);
    //秒杀活动发布时同步库存到缓存
    void publishPromo(Integer promoId);
}
