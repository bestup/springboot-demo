package com.export.excel.easyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.export.excel.easyexcel.model.IsAdminConverter;
import com.export.excel.easyexcel.model.User;
import com.export.excel.easyexcel.service.EasyExcelService;
import com.export.excel.easyexcel.service.UserDataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文档地址： https://www.yuque.com/easyexcel/doc/write#713a5706
 *
 * 这里有个问题需要说明一下，就是配置对应列的converter
 * 网上资源大多都是将对应的converter注册如下这种写法：registerConverter(new IsAdminConverter())
 * EasyExcel.write(response.getOutputStream(), User.class).registerConverter(new IsAdminConverter()).sheet("报表1").doWrite(userList);
 * 但是这样写会对所有的列生效，包括不想使用converter的列也都会被转换，所以只在对应的列上加了注解配置就可以了
 * 如果有特殊需要，在注册成所有列生效
 */
@RestController
@RequestMapping("/easy/excel")
public class EasyExcelController {

    @Autowired
    private EasyExcelService easyExcelService;

    /**
     * 导出单个sheet
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportOneSheet")
    public void exportOneSheet(HttpServletResponse response) throws IOException {
        List<User> userList = easyExcelService.findAll();

        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("用户信息报表", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), User.class).sheet("报表1").doWrite(userList);
    }

    /**
     * 导出多个sheet
     * @param response
     * @throws IOException
     */
    @GetMapping("/exportTwoSheet")
    public void exportTwoSheet(HttpServletResponse response) throws IOException {
        List<User> userList = easyExcelService.findAll();

        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("用户信息报表", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        //写多个sheet
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        WriteSheet sheet1 = EasyExcel.writerSheet(0,"用户信息1").head(User.class).build();
        WriteSheet sheet2 = EasyExcel.writerSheet(1,"用户信息2").head(User.class).build();
        excelWriter.write(userList, sheet1);
        excelWriter.write(userList, sheet2);
        excelWriter.finish();
    }

    /**
     * 导入单个sheet
     * @param file
     * @throws IOException
     */
    @PostMapping("/importOneSheet")
    public String importOneSheet(MultipartFile file) throws IOException {
        //监听器中，每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
        UserDataListener userDataListener = new UserDataListener();
        EasyExcel.read(file.getInputStream(), User.class, userDataListener).sheet().doRead();
        List<User> list = userDataListener.getList();
        for (User user : list) {
            System.out.println(JSON.toJSONString(user));
        }
        return "success";
    }

    /**
     * 导入多个sheet
     * @param file
     * @throws IOException
     */
    @PostMapping("/importTwoSheet")
    public String importTwoSheet(MultipartFile file) throws IOException {
        ExcelReader excelReader = null;
        InputStream in = null;

        in = file.getInputStream();
        excelReader = EasyExcel.read(in).build();

        UserDataListener sheet1Listener = new UserDataListener();
        UserDataListener sheet2Listener = new UserDataListener();

        //获取sheet对象
        ReadSheet readBoxServerSheet =
                EasyExcel.readSheet(0).head(User.class).registerReadListener(sheet1Listener).build();
        ReadSheet readPlatformSheet =
                EasyExcel.readSheet(1).head(User.class).registerReadListener(sheet2Listener).build();

        //读取数据
        excelReader.read(readBoxServerSheet, readPlatformSheet);

        System.out.println("---------------------分割线sheet1---------------------------");

        List<User> list1 = sheet1Listener.getList();
        for (User user : list1) {
            System.out.println(JSON.toJSONString(user));
        }

        System.out.println("---------------------分割线sheet2---------------------------");

        List<User> list2 = sheet2Listener.getList();
        for (User user : list2) {
            System.out.println(JSON.toJSONString(user));
        }
        return "success";
    }

}
