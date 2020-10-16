package com.demo.valid.controller;

import com.demo.valid.annotation.Insert;
import com.demo.valid.annotation.Update;
import com.demo.valid.entity.Goods;
import com.demo.valid.entity.Payment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @PostMapping("/add")
    public Object addGoods(@RequestBody @Validated Goods goods) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "success");
        map.put("data", goods);
        return map;
    }

    ///可以自定义注解进行分组校验， 一般项目会引入mybatis框架，使用mybatis框架中insert注解和update注解即可
    @PostMapping("/add1")
    public Object addGoods1(@RequestBody @Validated(Insert.class) Goods goods) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "success");
        map.put("data", goods);
        return map;
    }

    @PostMapping("/update")
    public Object update(@RequestBody @Validated(Update.class) Goods goods) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "success");
        map.put("data", goods);
        return map;
    }

    @PostMapping("/validEnum")
    public Object validEnum(@RequestBody @Validated Payment payment) {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "success");
        map.put("data", payment);
        return map;
    }

}
