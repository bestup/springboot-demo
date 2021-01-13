package com.demo.controller;

import com.demo.common.Result;
import com.demo.model.User;
import io.github.yedaxia.apidocs.ApiDoc;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 根据用户id查询
     * @param id 用户id
     * @return 用户列表
     */
    @ApiDoc(Result.class)
    @GetMapping("/findById")
    public String findById(String id) {
        return "user";
    }

    /**
     * 用户新增
     * @param user 用户实体
     * @return 成功/失败
     */
    @PostMapping("/add")
    public String add(@RequestBody User user) {
        return "success";
    }

    /**
     * 用户添加
     * @param user 用户实体
     * @return
     */
    @PostMapping("/addEntity")
    public String addEntity(User user) {
        return "success";
    }

}


