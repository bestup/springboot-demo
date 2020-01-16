package com.mq.demo.demo4;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author halo.l
 * @date 2020-01-13
 */
public class TestCustomer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
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

        // ---------------------上面代码和Producer 是一样-------------------------
        // 声明一个消费者,配置好获取消息的方式
        /**
         * queue:队列名
         * autoAck：是否自动ack，如果不自动ack，需要使用channel.ack、channel.nack、channel
         * .basicReject 进行消息应答
         */
        channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
                    throws IOException {
                String routingKey = envelope.getRoutingKey();
                long deliveryTag = envelope.getDeliveryTag();
                System.out.println("routingKey:" + routingKey + ",deliveryTag:" + deliveryTag);
                System.out.println("----msg------" + new String(body, "UTF-8"));
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 消息确认
                /**
                 * deliveryTag:该消息的index
                 * multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息
                 */
                channel.basicAck(deliveryTag, false);
            }

        });
    }
}
