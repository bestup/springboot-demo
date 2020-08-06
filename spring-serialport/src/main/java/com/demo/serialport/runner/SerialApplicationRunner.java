package com.demo.serialport.runner;

import com.demo.serialport.serial.SerialPortComOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

/**
 * @author halo.l
 * @date 2020/8/4
 */
//@Configuration
public class SerialApplicationRunner implements ApplicationRunner {

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;
    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 串口的属性配置在数据库中，在项目启动完以后才能加载到dao层的对象，需要将new出的对象注入到容器中
         * 有多少个串口就配置多少个bean
         */
        SerialPortComOne serialPortComOne = new SerialPortComOne();
        serialPortComOne.setPortName("COM1");
        serialPortComOne.setBaudRate(115200);
        serialPortComOne.setDataBits(8);
        serialPortComOne.setStopBits(1);
        serialPortComOne.setParity(0);
        //将new出的对象放入Spring容器中
        defaultListableBeanFactory.registerSingleton("serialPortComOne",serialPortComOne);
        //自动注入依赖
        autowireCapableBeanFactory.autowireBean(serialPortComOne);
        serialPortComOne.init();
    }

    /**
     * 销毁方法，在开发阶段，项目重启时将会关闭容器，同时也要关闭串口，否则再次启动时会报串口繁忙
     * @throws Exception
     */
    @PreDestroy
    public void destroy() throws Exception {
        SerialPortComOne serialPortComOne = (SerialPortComOne)defaultListableBeanFactory.getBean("serialPortComOne");
        serialPortComOne.close();
    }
}
