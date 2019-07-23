package com.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.User;
import com.demo.utils.RedisUtil;

@RestController
public class TaskController {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private HashOperations hashOperations;
	
	@Autowired
	private ValueOperations valueOperations;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@GetMapping(value="/task")
	public String task() {
		User user = new User();
		user.setId(UUID.randomUUID().toString().replaceAll("-",""));
		user.setUsername("user");
		user.setPassword("password");
		valueOperations.set("user", user);
		return "task";
	}
	
	@RequestMapping("taskDel")
	public String taskDel() {
		redisTemplate.delete("user");
		return "success";
	}

}
