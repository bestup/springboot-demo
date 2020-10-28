package com.demo.controller;

import com.demo.common.Result;
import com.demo.entity.User;
import com.demo.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/index")
    public String index() {
        return "index/index";
    }

    @ResponseBody
    @GetMapping("/findByPage")
    public Result userInfo(String nickname, Integer pageNum, Integer pageSize) {
        if(null == pageNum || 0 == pageNum) {
            pageNum = 1;
        }
        if(null == pageSize || 0 == pageSize) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.findByPage(nickname);
        PageInfo<User> pageInfo = new PageInfo(users);
        return Result.success(pageInfo);
    }


}
