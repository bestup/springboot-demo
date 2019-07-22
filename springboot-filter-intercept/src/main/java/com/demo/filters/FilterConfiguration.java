package com.demo.filters;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
	
	//注册自定义的过滤器
	@Bean
	public FilterRegistrationBean<Filter> registerFilter(){
		FilterRegistrationBean<Filter> registerFilter = new FilterRegistrationBean<>();
		registerFilter.setFilter(new MyFilter());
		registerFilter.addUrlPatterns("/*");
		registerFilter.setName("LogCostFilter");
		registerFilter.setOrder(1);
		return registerFilter;
	}
	
}
