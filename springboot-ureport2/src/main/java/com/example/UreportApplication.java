package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * 配置的最后一步就是让我们的项目加载UReport2的Spring配置文件ureport-console-context.xml，
 * 配置方法有很多，不再一一赘述，但同样不适合Spring Boot项目，在Spring Boot项目中，仅需加上一行注解即可@ImportResource
 *
 * 注意：只能在springboot启动类中加，不能在某个被扫描的config中加（不会生效）
 */

@ImportResource("classpath:ureport-console-context.xml")
//@PropertySource(value = "classpath:ureport.properties", encoding = "utf8")
@SpringBootApplication
public class UreportApplication {

    public static void main(String[] args) {
        SpringApplication.run(UreportApplication.class,args);
    }
}
