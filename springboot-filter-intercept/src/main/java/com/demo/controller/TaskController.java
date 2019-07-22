package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
	
	@GetMapping("task")
	public String task() {
		System.out.println("task controller");
		return "task";
	}
}
