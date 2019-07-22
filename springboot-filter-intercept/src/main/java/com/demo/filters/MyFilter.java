package com.demo.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFilter implements Filter {
	
	//过滤器属于servlet规范，要实现Filter接口，配置完过滤器以后，由于springBoot没有web.xml，所以要在配置类中注册
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		long start = System.currentTimeMillis();
		System.out.println("经过MyFilter--> 进");
		chain.doFilter(request,response);
		System.out.println("经过MyFilter--> 出");
        System.out.println("Execute cost="+(System.currentTimeMillis()-start));
	}

}
