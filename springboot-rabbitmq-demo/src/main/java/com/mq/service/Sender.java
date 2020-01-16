package com.mq.service;

import com.mq.config.RabbitMQConfig;
import com.mq.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author halo.l
 * @desc: 消息发送者
 */
@Component
@Slf4j
public class Sender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendDirectQueue(){
        User user = new User();
        user.setUserId("123123");
        user.setName("张三");

        log.info("【sendDirectQueue已发送消息】");

        // 第一个参数是指要发送到哪个队列里面， 第二个参数是指要发送的内容
        amqpTemplate.convertAndSend(RabbitMQConfig.queue, user.toString());

    }
}
