package com.demo.common.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.alibaba.fastjson.JSON;
import com.demo.common.enums.ResponseResultEnum;
import com.demo.common.exception.LoseTokenException;
import com.demo.common.response.ResponseData;
import com.demo.common.utils.JwtUtil;
import com.demo.common.utils.RedisUtil;

public class AjaxBasicAuthenticationFilter extends BasicAuthenticationFilter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	RedisUtil redisUtil;
	

	public AjaxBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	//@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//获取请求头中的Authorization信息
		String tokenHeader = request.getHeader(JwtUtil.TOKEN_HEADER);
		
		logger.info("tokenHeader:" + tokenHeader);
		
		String redisToken = null;
		try {
			redisToken = redisUtil.get(JwtUtil.getAppUserName(tokenHeader.replace(JwtUtil.TOKEN_PREFIX, "")));
		} catch (Exception e) {
			logger.info("wrong format of token");
		}
		
		logger.info("redisToken:" + redisToken);
		logger.info("result:" + !tokenHeader.replace(JwtUtil.TOKEN_PREFIX, "").equals(redisToken));
		
		try {
			if(StringUtils.isBlank(redisToken)) {
				throw new LoseTokenException("bad credentials exception");
			}
			if(!redisToken.equals(tokenHeader.replace(JwtUtil.TOKEN_PREFIX, ""))) {
				throw new LoseTokenException("bad credentials exception");
			}
		} catch (LoseTokenException e) {
			logger.info("bad credentials exception");
			response.setCharacterEncoding("utf-8");
			response.getWriter()
	        .write(JSON.toJSONString(ResponseData.failure(ResponseResultEnum.USER_LOGIN_FAILED.getCode(), ResponseResultEnum.USER_LOGIN_FAILED.getMessage())));
			return;
		}
		
		
		// 如果请求头中没有Authorization信息则直接放行了
		if (tokenHeader == null || !tokenHeader.startsWith(JwtUtil.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		// 如果请求头中有token，则进行解析，并且设置认证信息
		SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
		super.doFilterInternal(request, response, chain);
	}

	// 这里从token中获取用户信息并新建一个token
	private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
		String token = tokenHeader.replace(JwtUtil.TOKEN_PREFIX, "");
		String id = null;
		String username = null;
		try {
			id = JwtUtil.getAppUID(token);
			username = JwtUtil.getAppUserName(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (id != null) {
			return new UsernamePasswordAuthenticationToken(username, null,null);
		}
		return null;
	}

}
