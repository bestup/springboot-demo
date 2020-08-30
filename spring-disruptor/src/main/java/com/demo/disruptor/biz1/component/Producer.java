package com.demo.disruptor.biz1.component;

import com.demo.disruptor.biz1.event.MyEvent;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * 定义生产者
 */
public class Producer {

    private final RingBuffer<MyEvent> ringBuffer;

    public Producer(RingBuffer<MyEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void pushData(ByteBuffer byteBuffer){
        long sequence = ringBuffer.next();
        try {
            MyEvent even = ringBuffer.get(sequence);
            even.setValue(byteBuffer.getLong(0));
        } finally {
            ringBuffer.publish(sequence);
        }
    }

}
