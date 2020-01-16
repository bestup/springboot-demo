package com.mq.controller;

import com.mq.service.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanglong
 * @date 2020-01-14
 */
@RestController
public class MqController {

    @Autowired
    private Sender sender;

    @GetMapping("/send")
    public String sendDirectQueue(){
        sender.sendDirectQueue();
        return "ok";
    }
}
