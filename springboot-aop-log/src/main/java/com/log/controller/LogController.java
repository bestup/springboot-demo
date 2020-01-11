package com.log.controller;

import com.log.annotation.SystemLog;
import com.log.model.User;
import org.springframework.web.bind.annotation.*;

/**
 * @author halo.l
 * @date 2019-12-26
 */
@RestController
@RequestMapping("/test")
public class LogController {

    @SystemLog(module = "客户管理", description = "描述")
    @GetMapping("/getOne")
    public User getOne(Integer id, String name) throws Exception {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setAge(14);
        user.setAddress("北京");
        return user;
    }

    @SystemLog(module = "客户管理", description = "描述")
    @PostMapping("/getAdd")
    public User getAll(@RequestBody User user){
        return user;
    }


}
