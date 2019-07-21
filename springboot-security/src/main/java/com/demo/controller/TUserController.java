package com.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.TUser;
import com.demo.service.TUserService;

/**
 * @author author
 * @since 2019-06-23
 */
@RestController
public class TUserController {
	
	@Autowired
	TUserService tUserService;
	
	
	@GetMapping("find")
	public String findAll() {
		return "findAll";
	}
	
	@GetMapping("register")
	public String register(String username, String password) {
		TUser user = tUserService.register(username, password);
		if(null != user) {
			return "success";
		}
		return "fail";
	}
	
	
	
	
	

}

