package com.demo.disruptor.biz3.factory;

import com.demo.disruptor.biz3.event.VehicleIntoEvent;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class VehicleEventFactory implements EventFactory<VehicleIntoEvent> {

    @Override
    public VehicleIntoEvent newInstance() {
        return new VehicleIntoEvent();
    }
}
