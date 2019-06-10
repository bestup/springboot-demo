package com.logbak.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogBackController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("debug")
	public String debug() {
		logger.debug("debug");
		return "debug";
	}
	
	@GetMapping("info")
	public String info() {
		logger.info("info");
		return "info";
	}
}
