package com.mq.demo.demo1;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author halo.l
 * @date 2020-01-11
 * @desc: 广播模式， 给所有的订阅者发送消息
 */
public class TestCustomer1 {

    /**
     * 配置交换机
     */
    public final static String EXCHANGE_NAME="fanout_exchange";


    public static void main(String[] args) throws IOException, TimeoutException {

        //创建连接工厂，设置RabbitMQ地址
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");

        //创建一个新的连接
        Connection connection = factory.newConnection();

        //创建一个通道
        Channel channel = connection.createChannel();

        //交换机声明（参数为：交换机名称；交换机类型）
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        //获取一个临时队列
        String queueName = channel.queueDeclare().getQueue();

        //队列与交换机绑定（参数为：队列名称；交换机名称；routingKey忽略）
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" 接收到消息 '" + message + "'");
            }
        };

        //自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume(queueName, true, consumer);
    }
}
