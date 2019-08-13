package com.demo.service;

import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Backoff;

@Service
public class RemoteService {
	
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
	 * 
	 */
	@Retryable(value= {RemoteAccessException.class},maxAttempts = 5,backoff = @Backoff (delay = 2000L,multiplier = 1))
	public void call() throws Exception {
		System.out.println("do something...");
		throw new RemoteAccessException("RPC调用异常");
	}

	/**
	 * @Recover 用于@Retryable重试失败后处理方法，此注解注释的方法参数一定要是@Retryable抛出的异常，否则无法识别，可以在该方法中进行日志处理
	 */
	@Recover
	public void recover(RemoteAccessException e) {
		System.out.println(e.getMessage());
	}
}
