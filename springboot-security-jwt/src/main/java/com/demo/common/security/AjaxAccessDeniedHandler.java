package com.demo.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.demo.common.enums.ResponseResultEnum;
import com.demo.common.response.ResponseData;

/**
 * @description: 无权访问
 */
@Component
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
    	logger.info("无权访问");
    	httpServletResponse.setCharacterEncoding("utf-8");
    	httpServletResponse.getWriter()
        .write(JSON.toJSONString(ResponseData.failure(ResponseResultEnum.USER_NO_ACCESS.getCode(), ResponseResultEnum.USER_NO_ACCESS.getMessage())));
    }
}
