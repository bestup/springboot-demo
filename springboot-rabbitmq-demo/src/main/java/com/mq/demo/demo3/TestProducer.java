package com.mq.demo.demo3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author halo.l
 * @date 2020-01-13
 */
public class TestProducer {

    public static final String EXCHANGE_NAME = "topics_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");

        //创建一个新的连接
        Connection connection = factory.newConnection();

        //创建消息通道
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String[] routing_keys = new String[]{"usa.news","usa.weather","europe.news","europe.weather"};

        String[] messages = new String[]{"美国新闻","美国天气","欧洲新闻","欧洲天气"};

        for (int i = 0; i < routing_keys.length; i++) {
            String routingKey = routing_keys[i];
            String message = messages[i];
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message
                    .getBytes());
            System.out.printf("发送消息到路由：%s, 内容是: %s%n ", routingKey,message);

        }

        //关闭通道和连接
        channel.close();
        connection.close();
    }

}
