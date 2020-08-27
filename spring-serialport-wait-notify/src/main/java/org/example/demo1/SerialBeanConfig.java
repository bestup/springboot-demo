package org.example.demo1;

import jssc.SerialPortException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author halo.l
 * @date 2020/8/4
 */
//@Configuration
public class SerialBeanConfig {

    @Bean
    public SerialPortComOne serialPortComOne() throws SerialPortException {
        /**
         * 1、如果串口配置属性放在配置文件中，就可以在配置类中读取信息，实例化对象，
         * 此时串口对象已经以单例模式注入到spring容器中，有多少个串口，就需要配置多少个javabean
         *
         * 2、如果串口配置属性在数据库中，就要引入ApplicationRunner，参考SerialApplicationRunner
         */
        SerialPortComOne serialPortComOne = new SerialPortComOne();
        serialPortComOne.setPortName("COM1");
        serialPortComOne.setBaudRate(115200);
        serialPortComOne.setDataBits(8);
        serialPortComOne.setStopBits(1);
        serialPortComOne.setParity(0);
        serialPortComOne.init();
        return serialPortComOne;
    }

    @Bean
    public SerialPortComTwo serialPortComTwo() throws SerialPortException {
        /**
         * 1、如果串口配置属性放在配置文件中，就可以在配置类中读取信息，实例化对象，
         * 此时串口对象已经以单例模式注入到spring容器中，有多少个串口，就需要配置多少个javabean
         *
         * 2、如果串口配置属性在数据库中，就要引入ApplicationRunner，参考SerialApplicationRunner
         */
        SerialPortComTwo serialPortComTwo = new SerialPortComTwo();
        serialPortComTwo.setPortName("COM3");
        serialPortComTwo.setBaudRate(115200);
        serialPortComTwo.setDataBits(8);
        serialPortComTwo.setStopBits(1);
        serialPortComTwo.setParity(0);
        serialPortComTwo.init();
        return serialPortComTwo;
    }

}
