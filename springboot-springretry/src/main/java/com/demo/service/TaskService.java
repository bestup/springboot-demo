package com.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class TaskService{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 需要被重试的方法加上@Retryable(),就能在指定的异常出现情况下重试，而当默认的失败次数到达后（查看SimpleRetryPolicy可知，就是试3次），就会调用@Recover注解的方法，进行恢复.   
	 * 在@Retryable上，可以配置属性，更加细化
	 * @Retryable->value:指定异常类
	 * @Retryable->include：指定处理的异常类和value一样，默认为空，当exclude也为空时，默认所有异常
	 * @Retryable->exclude：指定异常不处理，默认空，当include也为空时，默认所有异常
	 * @Retryable->maxAttempts：最大重试次数。默认3次
	 * @Retryable->BackOff：补偿值，一般指失败后多久进行重试的延迟值
	 * 
	 * @Retryable->@Backoff->delay: 间隔时间 ， 如：2000L，间隔2s
	 * @Retryable->@Backoff->multiplier: 指定延迟倍数，比如delay=2000l,multiplier=2,则第一次重试为2秒，第二次为4秒，第三次为8秒
	 * @Async 异步执行，会从配置好的线程池中取一个线程来执行重试流程
	 */
	@Async(value="myThradPool")
	@Retryable(value= {Exception.class},maxAttempts = 5,backoff = @Backoff (delay = 2000L,multiplier = 1))
	public void task(String url, String content) throws Exception {
		
		//执行推送
		log.info("推送开始**********************************");
		
		log.info("要推送的地址：" + url);
		log.info("要推送的数据：" + content);
		log.info("当前执行推送的线程：" + Thread.currentThread().getName());
		
		throw new RetryException("RPC调用异常");
	}

	/**
	 * @Recover 用于@Retryable重试失败后处理方法，此注解注释的方法参数一定要是@Retryable抛出的异常，否则无法识别，可以在该方法中进行日志处理
	 */
	@Recover
	public void taskRecover(Exception e) {
		
		log.info("重试次数已达上限，重试失败---------------------------------------------");
	}


}
