package com.demo.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;

import com.demo.service.RemoteService;

@Configuration
@EnableRetry
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class RetryServiceMain {
	
	@Bean
    public RemoteService retryService(){
        return new RemoteService();
    }
	
	public static void main(String[] args) {
		
		final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RetryServiceMain.class);
        final RemoteService retryService = applicationContext.getBean(RemoteService.class);
        try {
			retryService.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
