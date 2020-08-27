package org.example.demo2;


import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import lombok.Data;
import lombok.SneakyThrows;
import org.example.demo1.WaitNotifyAll;
import org.example.demo2.entity.SerialPortCommon;
import org.example.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author halo.l
 * @date 2020/8/4
 */
@Data
public class SerialPortComOne {

    /**
     * 请求我发送指令获取状态信息的串口bean的名称
     */
    private String beanName;

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
        serialPort = new SerialPort(portName);
        //如果串口打开着，先关闭
        if(null != serialPort && serialPort.isOpened()) {
            serialPort.closePort();
        }
        //打开串口
        serialPort.openPort();
        serialPort.addEventListener(new SerialPortEventListener() {
            @SneakyThrows
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
                        String data = new String(bytes, "GBK");
                        SerialPortCommon serialPortCommon = (SerialPortCommon)SpringUtils.getBean(beanName);
                        serialPortCommon.notifyContinue(data);
                        break;
                    default:
                        break;
                }
            }
        });
        serialPort.setParams(baudRate, dataBits, stopBits, parity);
    }

    /**
     * 发送数据
     * @param beanName 是哪个串口请求我发送指令获取状态信息
     * @param sendStr 发送的指令
     * @throws UnsupportedEncodingException
     * @throws SerialPortException
     */
    public void send(String beanName, String sendStr) throws UnsupportedEncodingException, SerialPortException {
        this.beanName = beanName;
        serialPort.writeString(sendStr,"GBK");
    }

    public void close() throws SerialPortException {
        serialPort.closePort();
    }
}