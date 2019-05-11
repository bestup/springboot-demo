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
@RequestMapping("/order")
@Api(value = "OrderController",tags= "订单管理")
public class OrderController {
	
	@GetMapping("/findAll")
	@ApiOperation(value = "订单查询",notes="查询所有的订单信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "time", value = "订单时间",required=true)})
    public String findAll(@RequestParam(name="time",required=true)String time) {
        return time;
    }
	
}
