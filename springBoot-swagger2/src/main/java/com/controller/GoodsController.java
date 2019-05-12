package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Goods;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/goods")
@Api(value = "GoodsController",tags= "商品管理")
public class GoodsController {
	
	@GetMapping("/findAll")
	@ApiOperation(value = "商品查询",notes="查询所有的商品信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "type", value = "商品类型",required=true)})
    public String findAll(@RequestParam(name="type",required=true)String type) {
        return type;
    }
	
	@PostMapping("/add")
	@ApiOperation(value = "商品增加",notes="增加商品信息")
	//@ApiImplicitParam(name="goods",value="商品信息",paramType = "body", required = true)
    public String add(@RequestBody Goods goods) {
        return goods.toString();
    }

}
