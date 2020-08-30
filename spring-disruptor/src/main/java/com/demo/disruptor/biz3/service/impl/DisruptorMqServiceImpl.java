package com.demo.disruptor.biz3.service.impl;

import com.demo.disruptor.biz3.event.VehicleIntoEvent;
import com.demo.disruptor.biz3.handler.VehicleSendSMSHandler;
import com.demo.disruptor.biz3.service.DisruptorMqService;
import com.lmax.disruptor.RingBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DisruptorMqServiceImpl implements DisruptorMqService {

    @Autowired
    private RingBuffer<VehicleIntoEvent> vehicleIntoEventRingBuffer;

    @Override
    public void sayHelloMq(String data) {
        // 获取下一个Event槽的下标
        long sequence = vehicleIntoEventRingBuffer.next();
        try {
            // 给Event填充数据
            VehicleIntoEvent event = vehicleIntoEventRingBuffer.get(sequence);
            event.setVehicleNo(data);
            log.info("往消息队列中添加消息：{}", event);
        } catch (Exception e) {
            log.error("failed to add event to messageModelRingBuffer for : e = {},{}", e, e.getMessage());
        } finally {
            // 发布Event，激活观察者去消费，将sequence传递给改消费者
            // 注意最后的publish方法必须放在finally中以确保必须得到调用；如果某个请求的sequence未被提交将会堵塞后续的发布操作或者其他的producer
            vehicleIntoEventRingBuffer.publish(sequence);

        }
    }
}
