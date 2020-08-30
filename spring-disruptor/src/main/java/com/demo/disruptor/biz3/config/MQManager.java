package com.demo.disruptor.biz3.config;

import com.demo.disruptor.biz2.event.MyInParkingDataEvent;
import com.demo.disruptor.biz2.handler.MyParkingDataInDbHandler;
import com.demo.disruptor.biz2.handler.MyParkingDataSmsHandler;
import com.demo.disruptor.biz2.handler.MyParkingDataToKafkaHandler;
import com.demo.disruptor.biz3.event.VehicleIntoEvent;
import com.demo.disruptor.biz3.factory.VehicleEventFactory;
import com.demo.disruptor.biz3.handler.VehicleIntoDBHandler;
import com.demo.disruptor.biz3.handler.VehicleSendKafkaHandler;
import com.demo.disruptor.biz3.handler.VehicleSendSMSHandler;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class MQManager {

    @Autowired
    private VehicleIntoDBHandler vehicleIntoDBHandler;

    @Autowired
    private VehicleSendKafkaHandler vehicleSendKafkaHandler;

    @Autowired
    private VehicleSendSMSHandler vehicleSendSMSHandler;

    @Bean("messageModel")
    public RingBuffer<VehicleIntoEvent> messageModelRingBuffer() {
        // 定义用于事件处理的线程池，
        // Disruptor通过java.util.concurrent.ExecutorSerivce提供的线程来触发consumer的事件处理
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 指定事件工厂
        VehicleEventFactory factory = new VehicleEventFactory();

        // 指定ringbuffer字节大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率
        int bufferSize = 1024 * 256;

        // 单线程模式，获取额外的性能
        Disruptor<VehicleIntoEvent> disruptor = new Disruptor<>(factory, bufferSize, executor, ProducerType.SINGLE,
                new BlockingWaitStrategy());

        // 设置事件业务处理器---消费者,使用disruptor创建消费者组 VehicleIntoDBHandler 和 VehicleSendKafkaHandler
        EventHandlerGroup<VehicleIntoEvent> handlerGroup = disruptor.handleEventsWith(
                vehicleIntoDBHandler, vehicleSendKafkaHandler);

        // 当上面两个消费者处理结束后在消耗 smsHandler
        //VehicleSendSMSHandler vehicleSendSMSHandler = new VehicleSendSMSHandler();
        handlerGroup.then(vehicleSendSMSHandler);

        // 启动disruptor线程
        disruptor.start();

        // 获取ringbuffer环，用于接取生产者生产的事件
        RingBuffer<VehicleIntoEvent> ringBuffer = disruptor.getRingBuffer();

        return ringBuffer;
    }
}