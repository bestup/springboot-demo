package com.demo.disruptor.biz1.factory;

import com.demo.disruptor.biz1.event.MyEvent;
import com.lmax.disruptor.EventFactory;

/**
 * 定义事件工厂
 * 事件工厂定义了如何实例化第一步中定义的事件。Disruptor通过EventFactory在RingBuffer中预创建Event的实例。
 * 一个Event实例被用作一个数据槽，发布者发布前，先从RingBuffer获得一个Event的实例，然后往Event实例中插入数据，然后再发布到RingBuffer中，最后由Consumer获得Event实例并从中读取数据。
 */
public class MyEventFactory implements EventFactory<MyEvent> {
    @Override
    public MyEvent newInstance() {
        return new MyEvent();
    }
}
