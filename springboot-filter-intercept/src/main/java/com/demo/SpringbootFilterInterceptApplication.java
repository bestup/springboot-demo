package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.demo.filters")
public class SpringbootFilterInterceptApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootFilterInterceptApplication.class, args);
	}

}
