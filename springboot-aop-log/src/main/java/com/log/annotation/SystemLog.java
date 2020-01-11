package com.log.annotation;

import java.lang.annotation.*;

/**
 * @author tanglong
 * 自定义注解， 拦截日志
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    /**
     * 模块名称 系统管理-用户管理－列表页面
     * @return
     */
    String module() default "";

    String methods() default "";

    String description() default "";

}
