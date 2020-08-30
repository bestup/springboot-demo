package com.demo.disruptor.biz1.handler;

import com.demo.disruptor.biz1.event.MyEvent;
import com.lmax.disruptor.EventHandler;

public class MyEventHandler implements EventHandler<MyEvent> {
    @Override
    public void onEvent(MyEvent myEvent, long l, boolean b) throws Exception {
        System.out.println("event:" + myEvent);
    }
}
