package com.demo.service;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.TaskDO;

@Service
public class TaskServiceImpl {
	
	@Autowired
	private TaskDO taskDO; 
	
	public Map<String, Object> save( Map<String, Object> map) {
		return taskDO.save(map);
	}
	
	public Map<String, Object> find( Map<String, Object> map) {
		return taskDO.save(map);
	}
	
	
	
}
