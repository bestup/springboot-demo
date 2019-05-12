package com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.service.UserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2019-05-12
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("test")
	public String test() {
		return JSON.toJSONString(userService.findOne(1));
	}
	
	@GetMapping("selectList")
	public String selectList() {
		return JSON.toJSONString(userService.selectList(null));
	}
	
}

