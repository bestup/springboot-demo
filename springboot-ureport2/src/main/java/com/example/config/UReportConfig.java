package com.example.config;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UReportConfig {

    /**
     * 网络教程中配置ureport使用的servlet在web.xml中配置
     * 在Spring Boot项目中如何整合呢？
     * Spring Boot提供两种Servlet整合方式：
     * 注解扫描方式和组件注册方式，此处组件注册方式更加适用。
     * 首先，注册ServletRegistrationBean实例，
     * 并将UReportServlet实例作为构造参数传入ServletRegistrationBean实例，
     * 然后为ServletRegistrationBean实例增加Url映射。
     *
     * 注意：值为 "/ureport/*" 的url-pattern是一定不能变的，否则系统将无法运行
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean uReportServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new UReportServlet());
        servletRegistrationBean.addUrlMappings("/ureport/*");
        return servletRegistrationBean;
    }

}
