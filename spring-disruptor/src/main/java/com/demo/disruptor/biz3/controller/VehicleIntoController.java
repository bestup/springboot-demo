package com.demo.disruptor.biz3.controller;

import com.demo.disruptor.biz3.service.DisruptorMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleIntoController {

    @Autowired
    private DisruptorMqService disruptorMqService;

    @GetMapping("/vehicleInto")
    public String vehicleInto() {
        String vehicleNo = "鄂A-" + (int)(Math.random() * 100000);
        disruptorMqService.sayHelloMq(vehicleNo);
        return vehicleNo;
    }

}
