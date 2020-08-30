package com.demo.disruptor.biz3.handler;

import com.demo.disruptor.biz3.event.VehicleIntoEvent;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VehicleSendKafkaHandler implements EventHandler<VehicleIntoEvent> {

    @Override
    public void onEvent(VehicleIntoEvent myInParkingDataEvent, long sequence, boolean endOfBatch) throws Exception {
        // 获取当前线程id
        long threadId = Thread.currentThread().getId();
        // 获取车牌号
        String carLicense = myInParkingDataEvent.getVehicleNo();
        log.info(String.format("Thread Id %s 发送 %s 进入停车场信息给 kafka系统...", threadId, carLicense));
    }

}
