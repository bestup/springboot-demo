package com.mq.service;

import com.mq.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author halo.l
 * @desc: 消息接受者
 */
@Component
@Slf4j
public class Receiver {

    @RabbitListener(queues = RabbitMQConfig.queue)
    public void receiverDirectQueue() {
        log.info("【receiverDirectQueue监听到消息 - User 】");

        //false为不支持批量签收
        //channel.basicAck("RabbitMQ我已经签收了", false);
    }
}
