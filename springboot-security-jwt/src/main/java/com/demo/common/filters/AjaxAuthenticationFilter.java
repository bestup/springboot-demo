package com.demo.common.filters;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.demo.common.model.LoginUser;
import com.demo.common.utils.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description:UsernamePasswordAuthenticationFilter是登陆用户密码验证过滤器，自定义前后端分离的项目重写attemptAuthentication()
 */
public class AjaxAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		ObjectMapper mapper = new ObjectMapper();
		UsernamePasswordAuthenticationToken authRequest = null;
		if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
				|| request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
			try{
				// 从输入流中获取到登录的信息
				LoginUser loginUser = mapper.readValue(request.getInputStream(), LoginUser.class);
				authRequest = new UsernamePasswordAuthenticationToken(loginUser.getUsername(),loginUser.getPassword());
			} catch (IOException e) {
				e.printStackTrace();
				authRequest = new UsernamePasswordAuthenticationToken("", "");
			}
		}
		return this.getAuthenticationManager().authenticate(authRequest);
	}
}
