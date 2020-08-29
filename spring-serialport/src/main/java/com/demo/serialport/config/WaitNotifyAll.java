package com.demo.serialport.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class WaitNotifyAll {

    private String data;

    public WaitNotifyAll(){

    }
    public synchronized void notifyContinue(String data){
        this.data = data;
        notifyAll();
    }
    public synchronized void waitResult() throws InterruptedException {
        System.out.println("线程" + Thread.currentThread().getName() + "等待中");
        wait();
        System.out.println("线程" + Thread.currentThread().getName() + "被唤醒，继续执行" );
    }

}
