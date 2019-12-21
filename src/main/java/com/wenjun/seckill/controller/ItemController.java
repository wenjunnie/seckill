package com.wenjun.seckill.controller;

import com.wenjun.seckill.controller.viewobject.ItemVO;
import com.wenjun.seckill.error.BusinessException;
import com.wenjun.seckill.response.CommonReturnType;
import com.wenjun.seckill.service.ItemService;
import com.wenjun.seckill.service.model.ItemModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: wenjun
 * @Date: 2019/12/21 16:16
 */
@RestController
@RequestMapping("/item")
@CrossOrigin(allowedHeaders = "*",allowCredentials = "true")
public class ItemController {

    @Autowired
    private ItemService itemService;

    //创建商品
    @PostMapping(value = "/create")
    public CommonReturnType createItem(@RequestParam(name = "title") String title,
                                       @RequestParam(name = "description") String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock") Integer stock,
                                       @RequestParam(name = "imgUrl") String imgUrl) throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);

        ItemModel itemModelForReturn = itemService.createItem(itemModel);
        ItemVO itemVO = convertVOFromModel(itemModelForReturn);
        return CommonReturnType.create(itemVO);
    }

    //浏览某个商品
    @GetMapping(value = "/get")
    public CommonReturnType getItem(@RequestParam(name = "id") Integer id) {
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertVOFromModel(itemModel);
        return CommonReturnType.create(itemVO);
    }

    //浏览商品列表
    @GetMapping(value = "/list")
    public CommonReturnType getItemList() {
        List<ItemModel> itemModelList = itemService.listItem();
        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertVOFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVOList);
    }

    private ItemVO convertVOFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        return itemVO;
    }
}
