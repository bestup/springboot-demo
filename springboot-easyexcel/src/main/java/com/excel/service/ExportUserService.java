package com.excel.service;

import com.excel.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author halo.l
 * @date 2020-01-10
 */
@Service
public class ExportUserService {

    public List<User> getAll(){
        List<User> users = new ArrayList<>();
        User user  = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setName("管理员");
        user.setNickName("小利");
        user.setAddress("北京");
        user.setEmail("45612345@qq.com");
        user.setPhone("12545621235");
        user.setCreateTime(new Date());

        User user1  = new User();
        user1.setId(2L);
        user1.setUsername("admin");
        user1.setName("管理员");
        user1.setNickName("小利");
        user1.setAddress("北京");
        user1.setEmail("45612345@qq.com");
        user1.setPhone("12545621235");
        user1.setCreateTime(new Date());

        User user2  = new User();
        user2.setId(3L);
        user2.setUsername("admin");
        user2.setName("管理员");
        user2.setNickName("小利");
        user2.setAddress("北京");
        user2.setEmail("45612345@qq.com");
        user2.setPhone("12545621235");
        user2.setCreateTime(new Date());

        users.add(user);
        users.add(user1);
        users.add(user2);
        return users;
    }


}
