package com.wenjun.seckill.service.impl;

import com.wenjun.seckill.dao.PromoDOMapper;
import com.wenjun.seckill.dataobject.PromoDO;
import com.wenjun.seckill.service.ItemService;
import com.wenjun.seckill.service.PromoService;
import com.wenjun.seckill.service.model.ItemModel;
import com.wenjun.seckill.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author: wenjun
 * @Date: 2019/12/22 11:52
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //获取对应商品秒杀信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);
        //promoDO -> promoModel
        PromoModel promoModel = convertFromDataObject(promoDO);
        if (promoModel == null) {
            return null;
        }
        //判断当前时间秒杀活动情况
        if (promoModel.getStartDate().isAfterNow()) {
            promoModel.setStatus(1);
        } else if (promoModel.getEndDate().isBeforeNow()) {
            promoModel.setStatus(0);
        } else {
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    @Override
    public void publishPromo(Integer promoId) {
        //通过活动ID获取活动
        PromoDO promoDO = promoDOMapper.selectByPrimaryKey(promoId);
        if (promoDO == null || promoDO.getItemId() == 0) {
            return;
        }
        ItemModel itemModel = itemService.getItemById(promoDO.getItemId());
        //将库存同步到Redis内
        redisTemplate.opsForValue().set("promo_item_stock" + itemModel.getId(),itemModel.getStock());
    }

    private PromoModel convertFromDataObject(PromoDO promoDO) {
        if (promoDO == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setPromoPrice(BigDecimal.valueOf(promoDO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }
}
