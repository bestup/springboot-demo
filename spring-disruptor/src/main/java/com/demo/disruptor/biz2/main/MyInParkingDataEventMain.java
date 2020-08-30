package com.demo.disruptor.biz2.main;

import com.demo.disruptor.biz2.action.MyInParkingDataEventPublisher;
import com.demo.disruptor.biz2.event.MyInParkingDataEvent;
import com.demo.disruptor.biz2.handler.MyParkingDataInDbHandler;
import com.demo.disruptor.biz2.handler.MyParkingDataSmsHandler;
import com.demo.disruptor.biz2.handler.MyParkingDataToKafkaHandler;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 执行的Main方法 ，
 * 一个生产者（汽车进入停车场）；
 * 三个消费者（一个记录汽车信息，一个发送消息给系统，一个发送消息告知司机）
 * 前两个消费者同步执行，都有结果了再执行第三个消费者
 */
public class MyInParkingDataEventMain {

    public static void main(String[] args) {
        long beginTime=System.currentTimeMillis();
        int bufferSize = 2048; // 2的N次方
        try {
            // 创建线程池，负责处理Disruptor的四个消费者
            ExecutorService executor = Executors.newFixedThreadPool(4);

            // 初始化一个 Disruptor
            Disruptor<MyInParkingDataEvent> disruptor = new Disruptor<MyInParkingDataEvent>(new EventFactory<MyInParkingDataEvent>() {
                @Override
                public MyInParkingDataEvent newInstance() {
                    return new MyInParkingDataEvent(); // Event 初始化工厂
                }
            }, bufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());

            // 使用disruptor创建消费者组 MyParkingDataInDbHandler 和 MyParkingDataToKafkaHandler
            EventHandlerGroup<MyInParkingDataEvent> handlerGroup = disruptor.handleEventsWith(
                    new MyParkingDataInDbHandler(), new MyParkingDataToKafkaHandler());

            // 当上面两个消费者处理结束后在消耗 smsHandler
            MyParkingDataSmsHandler myParkingDataSmsHandler = new MyParkingDataSmsHandler();
            handlerGroup.then(myParkingDataSmsHandler);

            // 启动Disruptor
            disruptor.start();

            CountDownLatch countDownLatch = new CountDownLatch(1); // 一个生产者线程准备好了就可以通知主线程继续工作了
            // 生产者生成数据
            executor.submit(new MyInParkingDataEventPublisher(countDownLatch, disruptor));
            countDownLatch.await(); // 等待生产者结束

            disruptor.shutdown();
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("总耗时:"+(System.currentTimeMillis()-beginTime));
    }

}
