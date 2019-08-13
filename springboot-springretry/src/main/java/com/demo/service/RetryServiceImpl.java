package com.demo.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;

@Service
public class RetryServiceImpl{

	private AtomicInteger count = new AtomicInteger(1);
	
    @Retryable(value = { RemoteAccessException.class }, maxAttemptsExpression = "${retry.maxAttempts:10}", backoff = @Backoff(delayExpression = "${retry.backoff:1000}"))
    public void retry() {
        System.out.print("start to retry : " + count.getAndIncrement());
 
        throw new RemoteAccessException("here " + count.get());
    }
    
    @Recover
    public void recover(RemoteAccessException t) {
    	System.out.println("SampleRetryService.recover:{}"+ t.getClass().getName());
    }   
	
}
