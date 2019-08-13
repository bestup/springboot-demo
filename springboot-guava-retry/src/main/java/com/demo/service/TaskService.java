package com.demo.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.demo.common.RetryerFactory;
import com.github.rholder.retry.RetryException;

@Component
public class TaskService implements Runnable{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private String url;
	private String content;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public TaskService() {
	}
	
	public TaskService(String url, String content) {
		this.url = url;
		this.content = content;
	}
	
	public Boolean task() {
		String url = getUrl();
		String content = getContent();
		int[] arr = new int[1];
		arr[0] = 0;
		try {
			return RetryerFactory.retryer.call(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					String status = "fail";
					//如果返回false，则进行重试，这里为了测试，所以一直会返回false，重试也会一直进行
					if("success".equals(status)) {
						
						log.info("请求成功，不重试*******************************");
						return true;
					}
					arr[0] = arr[0] + 1;
					log.info("请求失败，重试开启**********************************count=" + arr[0] );
					return false;
				}
			});
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (RetryException e) {
			//重试已达上限仍未推送成功，则保存运单数据

			log.info("推送地址：" + url);
			log.info("线程：" + Thread.currentThread().getName());
			log.info("推送数据：" + content);
			log.info("重试已达上限仍未推送成功，运单数据已保存------------------------count=" + arr[0]);
		}
		return false;
	}
	
	@Override
	public void run() {
		task();
	}

}
