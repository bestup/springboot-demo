package com.excel.controller;

import com.excel.entity.User;
import com.excel.service.ExportUserService;
import com.excel.util.EasyExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author halo.l
 * @date 2020-01-10
 */
@RestController
@RequestMapping("/export")
public class ExportExcelController {

    @Autowired
    private ExportUserService exportUserService;

    /**
     * 基本的数据导出
     * @param response
     * @throws IOException
     */
    @GetMapping("/user")
    public void exportUserInfo(HttpServletResponse response) throws IOException {
        EasyExcelUtil.export2Web(response, "用户表", "用户信息", User.class,
                exportUserService.getAll());
    }

    /**
     * 带样式导出
     * @param response
     * @throws IOException
     */
    @GetMapping("/useraddhead")
    public void exportUserInfoAddhead(HttpServletResponse response) throws IOException {
        EasyExcelUtil.export2WebAddHead(response, "用户记录","用户表", "用户信息",
                User.class, exportUserService.getAll());
    }

    /**
     * 多个sheet导出
     * @param response
     * @throws IOException
     */
    @GetMapping("/usermoresheet")
    public void exportUserMoreSheet(HttpServletResponse response) throws IOException {
        EasyExcelUtil.export2WebMoreSheet(response, "用户表",
                User.class, exportUserService.getAll());
    }

}
