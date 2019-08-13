package com.demo.common;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;

/**
 * 配置Retryer
 */

@Component
public class RetryerFactory {
	
	public static Retryer<Boolean> retryer;
	
	/**
	 * 指数增长策略
	 * 第几次		指数			等待时间           	
	 * 1		2^1			2	*	15000(毫秒)
	 * 2		2^2			4	*	15000(毫秒)
	 * 3		2^3			8	*	15000(毫秒)
	 * 4		2^4			16	*	15000(毫秒)
	 * 5		2^5			32	*	15000(毫秒)
	 */
	static {
			retryer = RetryerBuilder.<Boolean>newBuilder()
			.retryIfException()		// 抛出异常会进行重试
			.retryIfResult(Predicates.equalTo(false)) // 返回false重试 
			.withWaitStrategy(WaitStrategies.exponentialWait(15000,3600,TimeUnit.SECONDS)) 	// 重试策略, 此处设置的指数增长策略
			.withStopStrategy(StopStrategies.stopAfterAttempt(6)) 	// 重试次数	
			.withRetryListener(new MyRetryListener())
			.build();	
	}
	
	/*static {
		retryer = RetryerBuilder.<Boolean>newBuilder()
			.retryIfException()		// 抛出异常会进行重试
			.retryIfResult(Predicates.equalTo(false)) // 返回false重试 
			.withWaitStrategy(WaitStrategies.incrementingWait(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS)) 	// 重试策略, 此处设置的递增等待时长策略
			.withStopStrategy(StopStrategies.stopAfterAttempt(5)) 	// 重试次数	
			.withRetryListener(new MyRetryListener())
			.build();	
	}*/

}
