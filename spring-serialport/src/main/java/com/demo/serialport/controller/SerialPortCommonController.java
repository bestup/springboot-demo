package com.demo.serialport.controller;

import com.demo.serialport.entity.SerialPortCommon;
import com.demo.serialport.serial.SerialPortComOne;
import jssc.SerialPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
public class SerialPortCommonController {

    @Lazy
    @Autowired
    private SerialPortCommon serialPortCommon1;


    @Lazy
    @Autowired
    private SerialPortCommon serialPortCommon2;

    @GetMapping("port1")
    public String port1() throws UnsupportedEncodingException, SerialPortException {
        serialPortCommon1.send("你好port1");
        return "success";
    }

    @GetMapping("port2")
    public String port2() throws UnsupportedEncodingException, SerialPortException {
        serialPortCommon2.send("你好port2");
        return "success";
    }


}
