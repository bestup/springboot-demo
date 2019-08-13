package com.demo.common;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 配置线程来执行重试推送
 */
@Configuration
public class ThreadExcutorConfig {
	
	private int corePoolSize = 10;	//线程池维护的最小线程数
	
	private int maxPoolSize = 50;	//线程池维护的最大线程数
	
	private int queueCapacity = 20; //缓存队列

	private int keepAliveSeconds = 60;	//允许的空闲时间
	
	private String threadNamePrefix = "meiyibao_push_data_";	//线程名前缀
	
	@Bean
	public ThreadPoolTaskExecutor myThradPool() {
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setKeepAliveSeconds(keepAliveSeconds);
		executor.setThreadNamePrefix(threadNamePrefix);
		// rejection-policy：当pool已经达到max size的时候，如何处理新任务  
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行 
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());	//对拒绝task的处理策略
		executor.initialize();
		return executor;
	}
	
}
