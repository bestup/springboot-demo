package com.demo.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.demo.common.enums.ResponseResultEnum;
import com.demo.common.response.ResponseData;

/**
 * @description: 用户登录失败时返回给前端的数据
 */
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
    	logger.info("用户登录失败");
    	httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.getWriter()
        .write(JSON.toJSONString(ResponseData.failure(ResponseResultEnum.USER_LOGIN_FAILED.getCode(), ResponseResultEnum.USER_LOGIN_FAILED.getMessage())));
    }

}
