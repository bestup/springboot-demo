package com.demo.serialport.controller;

import com.demo.serialport.serial.SerialPortComOne;
import jssc.SerialPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author halo.l
 * @date 2020/8/4
 */
@RestController
public class SerialPortController {

    @Lazy
    @Autowired
    private SerialPortComOne serialPortComOne;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/send")
    public String send() throws UnsupportedEncodingException, SerialPortException {
        serialPortComOne.send("你好");
        return "send success";
    }
}
