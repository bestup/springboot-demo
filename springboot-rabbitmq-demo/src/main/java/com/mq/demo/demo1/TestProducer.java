package com.mq.demo.demo1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author halo.l
 * @date 2020-01-11
 * @desc: 广播模式， 给所有的订阅者发送消息
 */
public class TestProducer {

    /**
     * 配置交换机
     */
    public static final String exchange_name = "fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建连接工厂，设置RabbitMQ地址
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");

        //创建一个新的连接
        Connection connection = connectionFactory.newConnection();

        //创建一个通道
        Channel channel = connection.createChannel();

        //交换机声明（参数为：交换机名称；交换机类型）
        channel.exchangeDeclare(exchange_name, "fanout");

        String msg1 = "减少库存";
        String msg2 = "减少订单";

        //发送消息到队列中
        channel.basicPublish(exchange_name, "", null, msg1.getBytes("utf-8"));
        channel.basicPublish(exchange_name, "", null, msg2.getBytes("utf-8"));

        //关闭通道和连接
        channel.close();
        connection.close();
    }

}
