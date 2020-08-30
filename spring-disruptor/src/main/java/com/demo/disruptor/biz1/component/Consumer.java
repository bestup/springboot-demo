package com.demo.disruptor.biz1.component;

import com.demo.disruptor.biz1.event.MyEvent;
import com.lmax.disruptor.WorkHandler;

import java.text.MessageFormat;

/**
 * 定义消费者
 */
public class Consumer implements WorkHandler<MyEvent> {

    @Override
    public void onEvent(MyEvent myEvent) throws Exception {
        long result = myEvent.getValue() + 1;

        System.out.println(MessageFormat.format("Data process : {0} + 1 = {1}", myEvent.getValue(), result));
    }
}
