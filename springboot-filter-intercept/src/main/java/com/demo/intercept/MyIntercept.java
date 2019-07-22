package com.demo.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyIntercept implements HandlerInterceptor{
	
	//preHandle是请求执行前执行的
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("MyIntercept--preHandle");
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	//postHandler是请求结束执行的，但只有preHandle方法返回true的时候才会执行
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("MyIntercept--postHandle");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	//是视图渲染完成后才执行，同样需要preHandle返回true，该方法通常用于清理资源等工作
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("MyIntercept--afterCompletion");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
