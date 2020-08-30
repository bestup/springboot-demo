package com.demo.disruptor.biz2.handler;

import com.demo.disruptor.biz2.event.MyInParkingDataEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import org.springframework.stereotype.Component;

/**
 * Handler 第一个消费者，负责保存进场汽车的信息
 *
 */

public class MyParkingDataInDbHandler implements  WorkHandler<MyInParkingDataEvent>, EventHandler<MyInParkingDataEvent>{

    @Override
    public void onEvent(MyInParkingDataEvent myInParkingDataEvent) throws Exception {
        long threadId = Thread.currentThread().getId(); // 获取当前线程id
        String carLicense = myInParkingDataEvent.getCarLicense(); // 获取车牌号
        System.out.println(String.format("Thread Id %s 保存 %s 到数据库中 ....", threadId, carLicense));
    }

    @Override
    public void onEvent(MyInParkingDataEvent myInParkingDataEvent, long sequence, boolean endOfBatch)
            throws Exception {
        this.onEvent(myInParkingDataEvent);
    }

}
