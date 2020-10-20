package com.demo.valid.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class GlobalConfig {

    /**
     * springboot validation默认会校验完所有字段，然后才抛出异常。
     * 可以通过一些简单的配置，开启Fail Fast模式，一旦校验失败就立即返回。
     * @return
     */
    @Bean
    public Validator validation() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

}
