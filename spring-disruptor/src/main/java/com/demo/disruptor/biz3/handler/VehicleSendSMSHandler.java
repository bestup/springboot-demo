package com.demo.disruptor.biz3.handler;

import com.demo.disruptor.biz3.event.VehicleIntoEvent;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VehicleSendSMSHandler implements EventHandler<VehicleIntoEvent> {
    @Override
    public void onEvent(VehicleIntoEvent myInParkingDataEvent, long sequence, boolean endOfBatch) throws Exception {
        // 获取当前线程id
        long threadId = Thread.currentThread().getId();
        // 获取车牌号
        String carLicense = myInParkingDataEvent.getVehicleNo();
        log.info(String.format("Thread Id %s 给  %s 的车主发送一条短信，并告知他计费开始了 ....", threadId, carLicense));
    }
}
