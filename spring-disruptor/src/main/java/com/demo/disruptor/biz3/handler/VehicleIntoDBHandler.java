package com.demo.disruptor.biz3.handler;

import com.demo.disruptor.biz3.event.VehicleIntoEvent;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VehicleIntoDBHandler implements EventHandler<VehicleIntoEvent>{

    @Override
    public void onEvent(VehicleIntoEvent vehicleIntoEvent, long l, boolean b) throws Exception {
        // 获取当前线程id
        long threadId = Thread.currentThread().getId();
        // 获取车牌号
        String carLicense = vehicleIntoEvent.getVehicleNo();
        log.info(String.format("Thread Id %s 保存 %s 到数据库中 ....", threadId, carLicense));
    }
}
