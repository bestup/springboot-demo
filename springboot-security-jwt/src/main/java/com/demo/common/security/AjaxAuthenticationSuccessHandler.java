package com.demo.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.demo.common.enums.ResponseResultEnum;
import com.demo.common.model.JwtUser;
import com.demo.common.response.ResponseData;
import com.demo.common.utils.JwtUtil;
import com.demo.common.utils.RedisUtil;



/**
 * @description: 用户登录成功时返回给前端的数据
 */	
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RedisUtil redisUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, 
    		HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
    	httpServletResponse.setCharacterEncoding("utf-8");
    	
    	JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        String token = JwtUtil.createJWT(jwtUser.getId(), jwtUser.getUsername());
        logger.info("jwtUser.getUsername():" + jwtUser.getUsername());
        logger.info("token:" + token);
        try {
			//redisUtil.remove(jwtUser.getUsername());
			redisUtil.save(jwtUser.getUsername(), token);
		} catch (Exception e) {
			logger.info("出错了");
		}
             
        logger.info("登录成功，token获取成功，已成功保存到redis");
        
        // 返回创建成功的token,但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的时候应该是 `Bearer token`
        httpServletResponse.setHeader("token", JwtUtil.TOKEN_PREFIX + token);
        httpServletResponse.getWriter()
        .write(JSON.toJSONString(ResponseData.success("登录成功", ResponseResultEnum.SUCCESS.getMessage())));
        
    }
}
