package com.demo.serialport.entity;

import com.demo.serialport.service.SerialService;
import com.demo.serialport.util.SpringUtils;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.io.UnsupportedEncodingException;


@Data
@Scope(value = "prototype")
public class SerialPortCommon {

    @Autowired
    private SerialService serialService;

    /**
     * 串口号
     */
    private String portName;
    /**
     * 波特率
     */
    private Integer baudRate;
    /**
     * 数据位
     */
    private Integer dataBits;
    /**
     * 停止位
     */
    private Integer stopBits;
    /**
     * 校验位
     */
    private Integer parity;

    private SerialPort serialPort;

    public void init() throws SerialPortException {
        if(null == serialService) {
            serialService = SpringUtils.getBean("serialService", SerialService.class);
        }

        serialPort = new SerialPort(portName);
        //如果串口打开着，先关闭
        if(null != serialPort && serialPort.isOpened()) {
            serialPort.closePort();
        }
        //打开串口
        serialPort.openPort();
        serialPort.addEventListener(new SerialPortEventListener() {
            @Override
            public void serialEvent(SerialPortEvent serialPortEvent) {
                switch (serialPortEvent.getEventType()) {
                    case SerialPortEvent.RXCHAR:
                        byte[] bytes = {};
                        try {
                            bytes = serialPort.readBytes();
                        } catch (SerialPortException e) {
                            e.printStackTrace();
                        }
                        if(null != bytes && bytes.length > 0) {
                            String data = null;
                            try {
                                data = new String(bytes, "GBK");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            serialService.show("串口号：" + portName + "，数据：" +data);
                            //System.out.println();
                        }else {
                            System.out.println("无数据");
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        serialPort.setParams(baudRate, dataBits, stopBits, parity);
    }

    public void send(String sendStr) throws UnsupportedEncodingException, SerialPortException {
        serialPort.writeString(sendStr,"GBK");
    }

    public void close() throws SerialPortException {
        serialPort.closePort();
    }


}
