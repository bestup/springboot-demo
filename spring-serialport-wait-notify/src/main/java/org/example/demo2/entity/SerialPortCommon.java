package org.example.demo2.entity;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import lombok.Data;
import lombok.SneakyThrows;
import org.example.demo2.SerialPortComOne;
import org.example.demo2.config.WaitNotifyAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

@Data
public class SerialPortCommon {

    @Autowired
    private SerialPortComOne serialPortComOne;

    @Autowired
    private WaitNotifyAll waitNotifyAll;

    /**
     * 当前串口在spring容器中bean的名称
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

    /**
     * 返回的数据
     */
    private String data;

    /**
     *
     * @throws SerialPortException
     */

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
                        serialPortComOne.send(beanName,"我是扫码器，" + beanName + "，我需要两对光栅的状态数据");
                        System.out.println("线程" + Thread.currentThread().getName() + "等待中");
                        //waitResult();
                        waitNotifyAll.waitResult();
                        data = waitNotifyAll.getData();
                        System.out.println("线程" + Thread.currentThread().getName() + "被唤醒，继续执行");
                        if(null != bytes && bytes.length > 0) {
                            String result = null;
                            try {
                                result = new String(bytes, "GBK");
                                System.out.println("数据 >:" + result);
                                System.out.println("通知返回，两对光栅的状态结果是：" + data);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
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

    public synchronized void waitResult() throws InterruptedException {
        wait();
    }

    public synchronized void notifyContinue(String data){
        this.data = data;
        notifyAll();
    }

    public void close() throws SerialPortException {
        serialPort.closePort();
    }
}
