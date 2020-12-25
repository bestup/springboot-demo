package com.example.controller;

import com.bstek.ureport.Utils;
import com.bstek.ureport.export.ExportManager;
import com.bstek.ureport.export.html.HtmlReport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 页面布局参考： springboot集成thymeleaf及thymeleaf 模板布局
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/index")
    public String index(Model model) throws Exception {
        return "index";
    }

    @GetMapping("/udesigner")
    public String upreview() throws Exception {
        return "redirect:/ureport/designer";
    }

    @ResponseBody
    @GetMapping("/health")
    public String health(){
        return "health";
    }

    @ResponseBody
    @GetMapping("/htmlReport")
    public String getHtml(HttpServletRequest request) {
        String file = "mysql:测试1.ureport.xml";
        ExportManager exportManager=(ExportManager) Utils.getApplicationContext().getBean(ExportManager.BEAN_ID);
        Map<String,Object> parameters=new HashMap<>();
        HtmlReport htmlReport = exportManager.exportHtml(file,request.getContextPath(),parameters);
        //输出Css样式
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<style type=\"text/css\">");
        stringBuilder.append(htmlReport.getStyle());
        stringBuilder.append("</style>");
        stringBuilder.append(htmlReport.getContent());
        return stringBuilder.toString();
    }

    @GetMapping("/exportExcel")
    public String exportExcel(HttpServletRequest request){
        /**
         * 不分页导出Excel
         * @param config 包含报表模版文件名、参数等信息的配置对象
         */
       // void exportExcel(ExportConfigure config);


        return null;
    }



}
