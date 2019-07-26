package com.demo.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskDO {
	
	@Autowired
	private MongoTemplate mongoTemplate; 
	
	private String collectionName = "message";
	
	/**
	 *  保存对象
	 */
	public Map<String, Object> save(Map<String, Object> map) {
		return mongoTemplate.save(map, collectionName);
	}

}
