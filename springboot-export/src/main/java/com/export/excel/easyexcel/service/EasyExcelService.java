package com.export.excel.easyexcel.service;

import com.export.excel.easyexcel.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EasyExcelService {

    public List<User> findAll(){
        List<User> list = new ArrayList<User>();
        User user = new User();
        user.setId("12312312313");
        user.setName("王菲飞");
        user.setPhone("15623652563");
        user.setAddress("上海");
        user.setAge(32);
        user.setHeight(1.681);
        user.setBirthday(new Date());
        user.setAmount(new BigDecimal("0.361"));
        user.setIsAdmin(0);
        list.add(user);

        User user1 = new User();
        user1.setId("12312312313");
        user1.setName("王菲飞");
        user1.setPhone("15623652563");
        user1.setAddress("上海");
        user1.setAge(32);
        user1.setHeight(1.682);
        user1.setBirthday(new Date());
        user1.setAmount(new BigDecimal("0.362"));
        user1.setIsAdmin(0);
        list.add(user1);

        User user2 = new User();
        user2.setId("12312312313");
        user2.setName("王菲飞");
        user2.setPhone("15623652563");
        user2.setAddress("上海");
        user2.setAge(32);
        user2.setHeight(1.683);
        user2.setBirthday(new Date());
        user2.setAmount(new BigDecimal("0.363"));
        user2.setIsAdmin(0);
        list.add(user2);
        return list;
    }


}
