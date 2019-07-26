package com.demo.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.demo.common.enums.ResponseResultEnum;
import com.demo.common.response.ResponseData;
import com.demo.common.utils.JwtUtil;
import com.demo.common.utils.RedisUtil;

/**
 * @description: 登出成功
 */
@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisUtil redisUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
    	
    	//获取请求头中的Authorization信息
		String tokenHeader = httpServletRequest.getHeader(JwtUtil.TOKEN_HEADER).replace(JwtUtil.TOKEN_PREFIX, "");
		String username = null;
		try {
			username = JwtUtil.getAppUserName(tokenHeader);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	redisUtil.remove(username);
    	
    	logger.info("登出成功，已成功从redis清除token");
    	
    	httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.getWriter()
        .write(JSON.toJSONString(ResponseData.success("登出成功",ResponseResultEnum.USER_LOGOUT_SUCCESS.getMessage())));
    }

}
