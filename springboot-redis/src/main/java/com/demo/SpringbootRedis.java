package com.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.demo.mapper")
@EnableCaching
public class SpringbootRedis {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRedis.class, args);
	}

}
