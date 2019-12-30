package com.wenjun.seckill.controller;

import com.wenjun.seckill.response.CommonReturnType;
import com.wenjun.seckill.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wenjun
 * @Date: 2019/12/30 15:07
 */
@RestController
@RequestMapping("/promo")
@CrossOrigin(allowedHeaders = "*",allowCredentials = "true")
public class PromoController {

    @Autowired
    private PromoService promoService;

    @GetMapping(value = "/publish")
    public CommonReturnType publishPromo(@RequestParam(name = "id") Integer id) {
        promoService.publishPromo(id);
        return CommonReturnType.create("缓存同步成功");
    }
}
