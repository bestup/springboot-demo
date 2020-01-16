package com.mq.demo.demo2;

import com.mq.util.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author halo.l
 * @desc: 指定队列模式， 消息来了，只发给指定的 Queue, 其他Queue 都收不到, 接收的消费者会分食队列中消息
 */
public class TestDriectProducer {
    public final static String QUEUE_NAME="direct_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQUtil.checkServer();

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("localhost");
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();

        for (int i = 0; i < 5; i++) {
            String message = "direct 消息 " +i;
            //发送消息到队列中
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println("发送消息： " + message);
        }

        //关闭通道和连接
        channel.close();
        connection.close();
    }
}