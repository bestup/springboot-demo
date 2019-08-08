package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** TaskService
 * halo.l  2019年8月8日
 * TODO
 */
@Service
public class TaskService {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public void task(String name) {
		log.info("TaskService++++++++++++++++++++++++++" + name);
	}
}
