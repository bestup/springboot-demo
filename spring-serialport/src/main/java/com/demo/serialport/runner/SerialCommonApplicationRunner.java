package com.demo.serialport.runner;

import com.demo.serialport.entity.SerialPortCommon;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SerialCommonApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SerialPortCommon serialPortCommon1 = new SerialPortCommon();
        serialPortCommon1.setPortName("COM1");
        serialPortCommon1.setBaudRate(115200);
        serialPortCommon1.setDataBits(8);
        serialPortCommon1.setStopBits(1);
        serialPortCommon1.setParity(0);
        serialPortCommon1.init();

        SerialPortCommon serialPortCommon2 = new SerialPortCommon();
        serialPortCommon2.setPortName("COM3");
        serialPortCommon2.setBaudRate(115200);
        serialPortCommon2.setDataBits(8);
        serialPortCommon2.setStopBits(1);
        serialPortCommon2.setParity(0);
        serialPortCommon2.init();
    }
}
