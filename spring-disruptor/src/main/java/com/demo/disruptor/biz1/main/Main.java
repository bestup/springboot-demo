package com.demo.disruptor.biz1.main;

import com.demo.disruptor.biz1.component.Consumer;
import com.demo.disruptor.biz1.component.Producer;
import com.demo.disruptor.biz1.event.MyEvent;
import com.demo.disruptor.biz1.factory.MyEventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadFactory;

public class Main {
    /**
     * 消费者数量
     */
    private static final int CONSTOMER_SIZE = 10;

    /**
     * 生产者数量
     */
    private static final int PRODUCER_SIZE = 100;

    public static void main(String[] args) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long start = System.currentTimeMillis();

        MyEventFactory factory = new MyEventFactory();
        int buffersize = 1024;
        Disruptor<MyEvent> disruptor = new Disruptor<MyEvent>(factory, buffersize, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });

        Consumer[] consumers = new Consumer[CONSTOMER_SIZE];
        for (int i = 0; i < CONSTOMER_SIZE; i++) {
            consumers[i] = new Consumer();
        }

        disruptor.handleEventsWithWorkerPool(consumers);
        disruptor.start();

        RingBuffer<MyEvent> ringBuffer = disruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long i = 0; i < PRODUCER_SIZE; i++) {
            bb.putLong(0, i);
            producer.pushData(bb);
            System.out.println("Success producer data : " + i);
        }
        long end = System.currentTimeMillis();

        disruptor.shutdown();

        System.out.println("Total time : " + (end - start));




    }
}
