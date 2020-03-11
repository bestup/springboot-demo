package com.demo.controller;

import com.demo.entity.User;
import com.demo.service.UserService;
import com.demo.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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

	@Autowired
	private UserService userService;
	
	@GetMapping(value="/task")
	public String task() {
		User user = new User();
		user.setId(1);
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

	@RequestMapping("get")
	public User get(Integer id) {
		return userService.get(id);
	}

	@RequestMapping("save")
	public User save(@RequestBody User user) {
		return userService.saveOrUpdate(user);
	}

	@RequestMapping("del")
	public String del(Integer id) {
		userService.delete(id);
		return "success";
	}

}
