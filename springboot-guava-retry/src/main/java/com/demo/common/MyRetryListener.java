package com.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;

/**
 * 配置重试监听
 */

@Component
public class MyRetryListener implements RetryListener{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public <Boolean> void onRetry(Attempt<Boolean> attempt) {
		
		// 距离上一次重试的时间间隔
		//log.info("距上一次重试的间隔时间为:" + attempt.getDelaySinceFirstAttempt());
        
		// 重试次数
		//log.info("重试次数：" + attempt.getAttemptNumber());

		// 重试过程是否有异常
		//log.info("重试过程是否有异常：" + attempt.hasException());
	}
}
