package com.export.word.freemarker.controller;

import com.alibaba.fastjson.JSON;
import com.export.word.freemarker.model.DocUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * FreeMarker生成word文档的功能是由XML+FreeMarker来实现的。先把word文件另存为xml，在xml文件中插入特殊的字符串占位符，
 * 将xml翻译为FreeMarker模板，最后用java来解析FreeMarker模板，编码调用FreeMarker实现文本替换并输出Doc。
 *
 * 优点：比Java2word功能强大，也是纯Java编程。
 * 缺点：生成的文件本质上是xml，不是真正的word文件格式，有很多常用的word格式无法处理或表现怪异，比如：超链、换行、乱码、部分生成的文件打不开等。　
 */
@RestController
@RequestMapping("/freemarker/word")
public class FreeMarkerWordController {

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException, TemplateException {

        //基于模板填充word文件
        String rootPath = Class.class.getClass().getResource("/").getPath();
        //1、创建配置实例
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File(rootPath));

        //2、设置编码
        configuration.setDefaultEncoding("utf-8");

        //3、加载模板
        Template template = configuration.getTemplate("freemarker.ftl");

        //4、创建数据模型
        Map<String, String> map = new HashMap<>();
        map.put("id", "1234456");
        map.put("name", "张阿森");
        map.put("amount", "32.56");

        //5、输出临时文件
        String path = System.getProperty("java.io.tmpdir") + File.separator;
        File outFile = File.createTempFile(path, ".doc");

        //6、将模板和数据模型合并生成文件
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
        //这两种方式都行
        //Writer out1 =new FileWriter(outFile);

        //7、生成文件
        template.process(map, out);
        //8、关闭流
        out.flush();
        out.close();

        //下载文件
        InputStream fin = null;
        try{
            //fin = new BufferedInputStream(new FileInputStream(outFile));
            fin = new FileInputStream(outFile);
            // 清空response
            //response.reset();
            String fileName ="简历.doc";
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件名
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));

            byte[] buffer = new byte[1024];  // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while((bytesToRead = fin.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesToRead);
            }
        }finally {
            if (fin != null) {
                fin.close();
            }
            if (outFile != null) {
                // 删除临时文件
                outFile.deleteOnExit();
            }
        }
    }



}
