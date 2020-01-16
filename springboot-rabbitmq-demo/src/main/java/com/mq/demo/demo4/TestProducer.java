package com.mq.demo.demo4;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author halo.l
 * @date 2020-01-13
 */
public class TestProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 默认链接的主机名,RabbitMQ-Server安装在本机，所以可以直接用127.0.0.1
        factory.setHost("127.0.0.1");
        // 创建链接
        Connection connection = factory.newConnection();
        // 创建信息管道
        Channel channel = connection.createChannel();
        // 创建一个名为queue01的队列，防止队列不存在
        String queueName = "queue01";
        // 进行信息声明 1.队列名2.是否持久化，3是否局限与链接，4不再使用是否删除，5其他的属性
        channel.queueDeclare(queueName, false, false, false, null);
        String msg = "Hello World!";

        // 发送消息
        // 在RabbitMQ中，消息是不能直接发送到队列，它需要发送到交换器（exchange）中。
        // 第一参数空表示使用默认exchange，第二参数表示路由键（routingKey）在该模型下必须与队列名相同，第四参数是发送的消息是（字节数组）
        channel.basicPublish("", queueName, null, msg.getBytes());

        System.out.println("发送  message[" + msg + "] to " + queueName + " success!");

        // 关闭管道
        channel.close();
        // 关闭连接
        connection.close();
    }
}
