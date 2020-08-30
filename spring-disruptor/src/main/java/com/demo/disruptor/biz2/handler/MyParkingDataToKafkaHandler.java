package com.demo.disruptor.biz2.handler;

import com.demo.disruptor.biz2.event.MyInParkingDataEvent;
import com.lmax.disruptor.EventHandler;

/**
 * 第二个消费者，负责发送通知告知工作人员(Kafka是一种高吞吐量的分布式发布订阅消息系统)
 */
public class MyParkingDataToKafkaHandler implements EventHandler<MyInParkingDataEvent>{

    @Override
    public void onEvent(MyInParkingDataEvent myInParkingDataEvent, long sequence, boolean endOfBatch)
            throws Exception {
        long threadId = Thread.currentThread().getId(); // 获取当前线程id
        String carLicense = myInParkingDataEvent.getCarLicense(); // 获取车牌号
        System.out.println(String.format("Thread Id %s 发送 %s 进入停车场信息给 kafka系统...", threadId, carLicense));
    }
}