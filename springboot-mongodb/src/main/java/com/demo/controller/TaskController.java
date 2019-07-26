package com.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.TaskServiceImpl;

@RestController
public class TaskController {
	
	@Autowired
	private TaskServiceImpl taskServiceImpl;
	
	@PostMapping("save")
	public Map<String, Object> save(@RequestBody Map<String, Object> map) {
		return taskServiceImpl.save(map);
	}

}
