package com.mq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author halo.l
 * @date 2020-01-13
 */
@Configuration
public class RabbitMQConfig {

    public static final String queue = "direct_queue";

    @Bean
    public Queue directQueue(){
        // 第一个参数是队列名字， 第二个参数是指是否持久化
        return new Queue(queue, true);
    }

}
