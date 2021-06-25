package com.oauth2.client.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class LoginController {

    String wxHost = "http://127.0.0.1:9901";

    String redirectUrl = "http://127.0.0.1:9900/loginSuccess";

    /**
     * 应用登录页面
     * @return
     */
    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 请求微信进行授权登录
     */
    @GetMapping("/wxLogin")
    public void wxLogin(HttpServletResponse response) throws IOException {
        String state = RandomUtil.randomString(5);
        response.sendRedirect(wxHost + "/authorize?responseType=code&clientId=123456&redirectUrl=" + redirectUrl + "&scope=scope" + "&state=" + state);
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(String code, String state, Model model) throws IOException {
        log.info("code -> " + code);
        log.info("state -> " + state);

        //再根据获取到的code换access_token
        String url = wxHost + "/getToken?code=" + code;

        RestTemplate restTemplate = new RestTemplateBuilder().build();
        JSONObject forObject = restTemplate.getForObject(url, JSONObject.class);

        log.info(forObject.toJSONString());


        //通过token获取用户信息
        String accessToken = (String)forObject.get("accessToken");
        String infoUrl = wxHost + "/getUserInfo?accessToken=" + accessToken;
        JSONObject hashMap = restTemplate.getForObject(infoUrl, JSONObject.class);
        log.info(hashMap.toJSONString());

        model.addAttribute("username", hashMap.getString("username"));
        model.addAttribute("address", hashMap.getString("address"));

        return "index";
    }

}
