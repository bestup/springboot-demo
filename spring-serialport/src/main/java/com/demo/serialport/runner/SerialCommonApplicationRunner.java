package com.demo.serialport.runner;

import com.demo.serialport.entity.SerialPortCommon;
import com.demo.serialport.serial.SerialPortComOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class SerialCommonApplicationRunner implements ApplicationRunner {

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;
    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SerialPortCommon serialPortCommon1 = new SerialPortCommon();
        serialPortCommon1.setPortName("COM1");
        serialPortCommon1.setBaudRate(115200);
        serialPortCommon1.setDataBits(8);
        serialPortCommon1.setStopBits(1);
        serialPortCommon1.setParity(0);

        SerialPortCommon serialPortCommon2 = new SerialPortCommon();
        serialPortCommon2.setPortName("COM3");
        serialPortCommon2.setBaudRate(115200);
        serialPortCommon2.setDataBits(8);
        serialPortCommon2.setStopBits(1);
        serialPortCommon2.setParity(0);
        //将new出的对象放入Spring容器中
        defaultListableBeanFactory.registerSingleton("serialPortCommon1",serialPortCommon1);
        defaultListableBeanFactory.registerSingleton("serialPortCommon2",serialPortCommon2);
        //自动注入依赖
        autowireCapableBeanFactory.autowireBean(serialPortCommon1);
        autowireCapableBeanFactory.autowireBean(serialPortCommon2);
        serialPortCommon1.init();
        serialPortCommon2.init();
    }

    /**
     * 销毁方法，在开发阶段，项目重启时将会关闭容器，同时也要关闭串口，否则再次启动时会报串口繁忙
     * @throws Exception
     */
    @PreDestroy
    public void destroy() throws Exception {
        SerialPortCommon serialPortCommon1 = (SerialPortCommon)defaultListableBeanFactory.getBean("serialPortCommon1");
        SerialPortCommon serialPortCommon2 = (SerialPortCommon)defaultListableBeanFactory.getBean("serialPortCommon2");
        serialPortCommon1.close();
        serialPortCommon2.close();
    }
}
