package com.demo.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

//这是过滤器实现的第二种写法，@WebFilter这个注解是Servlet3.0的规范，并不是Spring boot提供的
//除了这个注解以外，我们还需在springBoot启动类中加另外一个注解：@ServletComponetScan，指定扫描的包。
@WebFilter(urlPatterns = "/*", filterName = "logFilter2")
public class MyWebFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		long start = System.currentTimeMillis();
		System.out.println("经过MyWebFilter--> 进");
		chain.doFilter(request,response);
		System.out.println("经过MyWebFilter--> 出");
        System.out.println("Execute cost="+(System.currentTimeMillis()-start));
	}

}
