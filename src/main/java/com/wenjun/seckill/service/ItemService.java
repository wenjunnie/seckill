package com.wenjun.seckill.service;

import com.wenjun.seckill.error.BusinessException;
import com.wenjun.seckill.service.model.ItemModel;

import java.util.List;

/**
 * @Author: wenjun
 * @Date: 2019/12/20 15:02
 */
public interface ItemService {
    ItemModel createItem(ItemModel itemModel) throws BusinessException;
    List<ItemModel> listItem();
    ItemModel getItemById(Integer id);
}
