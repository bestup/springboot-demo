package com.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.TUser;
import com.demo.service.TUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author author
 * @since 2019-06-23
 */
@RestController
@Api(value = "TUserController",tags= "用户登录和注册")
public class TUserController {
	
	@Autowired
	TUserService tUserService;
	
	
	@GetMapping("find")
	public String findAll() {
		return "findAll";
	}
	
	@GetMapping("register")
	@ApiOperation(value = "商品查询",notes="查询所有的商品信息")
	public String register(String username, String password) {
		TUser user = tUserService.register(username, password);
		if(null != user) {
			return "success";
		}
		return "fail";
	}
	
	
	
	
	

}

