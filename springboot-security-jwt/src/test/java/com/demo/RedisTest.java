package com.demo;


import com.demo.common.utils.RedisUtil;

public class RedisTest {
	
	
	public static void main(String[] args) {
		RedisUtil redisUtil = new RedisUtil();
		boolean a = redisUtil.existsKey("a");
		System.out.println(a);
		
	}
}
