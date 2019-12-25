package com.excel.controller;

import com.excel.model.User;
import com.excel.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author halo.l
 * @date 2019-12-25
 */
@RestController
@RequestMapping("excel")
@Slf4j
public class ExcelController {

    /**
     * easyexcel导出Excel到web
     * @param response
     */
    @GetMapping("/export2Web")
    public void export2Web(HttpServletResponse response) {
        try {
            User u1 = new User();
            u1.setId(1);
            u1.setName("张三");
            u1.setAge(12);
            u1.setAddress("北京");
            u1.setPhone("175645123");
            u1.setEmail("4545614564@qq.com");

            User u2 = new User();
            u2.setId(1);
            u2.setName("张三");
            u2.setAge(12);
            u2.setAddress("北京");
            u2.setPhone("175645123");
            u2.setEmail("4545614564@qq.com");

            List<User> list = new ArrayList<>();
            list.add(u1);
            list.add(u2);

            ExcelUtils.export2Web(response, "用户表", "用户信息", User.class, list);
        } catch (Exception e) {
            log.error("报表导出异常:", e);
        }
    }

}
