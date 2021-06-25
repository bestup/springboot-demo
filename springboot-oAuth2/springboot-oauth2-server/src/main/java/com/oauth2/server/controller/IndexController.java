package com.oauth2.server.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 给第三方提供服务
 */
@Controller
public class IndexController {

    private static String redirectUrlStr = "";
    private static String stateStr = "";
    private static String codeStr = "abcde";
    private static String accessTokenStr = "12313123adsadadqweqweqeqwe123";

    /**
     * 用户访问客户端，后者将前者导向认证服务器，即跳转到登录页面
     * @param responseType
     * @param clientId  应用id，这里以clientId为例，
     * @param redirectUrl 回调地址
     * @param scope
     * @param state 随机字符串，回调会返回给请求方
     * @return
     */
    @GetMapping("/authorize")
    public String authorize(String responseType, String clientId, String redirectUrl, String scope, String state, ModelAndView model) throws Exception {
        //校验clientId合法性，加入为123456
        if(StringUtils.isBlank(clientId) || !"123456".equals(clientId)) {
            //跳转到其他异常页面
            throw new Exception("身份有误");
        }

        redirectUrlStr = redirectUrl;
        stateStr = state;
        return "login";
    }

    @ResponseBody
    @PostMapping("/login")
    public JSONObject login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //服务登录成功
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("redirectUrl", redirectUrlStr);
        jsonObject.put("code", codeStr);
        jsonObject.put("state", stateStr);
        return jsonObject;
    }

    /**
     * 获取身份令牌
     * @param code
     * @return
     */
    @ResponseBody
    @GetMapping("/getToken")
    public Map<String, Object> getToken(String code ) throws IOException {
        if(codeStr.equals(code)) {
            JSONObject map = new JSONObject();
            map.put("code", 0);
            map.put("accessToken", accessTokenStr);
            map.put("expireTime", 7200);
            return map;
        }
        return null;
    }

    /**
     * 获取用户信息
     * @param accessToken
     * @return
     */
    @ResponseBody
    @GetMapping("/getUserInfo")
    public Map<String, Object> getUserInfo(String accessToken ) throws IOException {
        // 验证accessToken，返回用户信息
        if(accessTokenStr.equals(accessToken)) {
            Map<String, Object> map = new HashMap<>();
            map.put("username", "李四");
            map.put("address", "上海市");
            map.put("sex", "男");
            return map;
        }
        return null;
    }


}
