package org.example.demo2.config;


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
        wait();
    }

}
