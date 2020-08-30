package com.demo.disruptor.biz2.handler;

import com.demo.disruptor.biz2.event.MyInParkingDataEvent;
import com.lmax.disruptor.EventHandler;
import org.springframework.stereotype.Component;

/**
 * 第三个消费者，sms短信服务，告知司机你已经进入停车场，计费开始。
 */

public class MyParkingDataSmsHandler implements EventHandler<MyInParkingDataEvent>{

    @Override
    public void onEvent(MyInParkingDataEvent myInParkingDataEvent, long sequence, boolean endOfBatch)
            throws Exception {
        long threadId = Thread.currentThread().getId(); // 获取当前线程id
        String carLicense = myInParkingDataEvent.getCarLicense(); // 获取车牌号
        System.out.println(String.format("Thread Id %s 给  %s 的车主发送一条短信，并告知他计费开始了 ....", threadId, carLicense));
    }

}
