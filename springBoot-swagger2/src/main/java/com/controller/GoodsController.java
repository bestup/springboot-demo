package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
