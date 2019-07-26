package com.demo.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.demo.common.enums.ResponseResultEnum;
import com.demo.common.response.ResponseData;

/**
 * @description: 用户未登录时返回给前端的数据
 */
@Component
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
    	logger.info("用户未登录");
    	httpServletResponse.setCharacterEncoding("utf-8");
    	httpServletResponse.getWriter()
    	.write(JSON.toJSONString(ResponseData.failure(ResponseResultEnum.USER_NEED_AUTHORITIES.getCode(), ResponseResultEnum.USER_NEED_AUTHORITIES.getMessage())));
    }
}
