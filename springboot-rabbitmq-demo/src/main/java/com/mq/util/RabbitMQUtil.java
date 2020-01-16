package com.mq.util;

import cn.hutool.core.util.NetUtil;

import javax.swing.*;

/**
 * @author halo.l
 * @date 2020-01-11
 */
public class RabbitMQUtil {

    public static void main(String[] args) {
        checkServer();
    }
    public static void checkServer() {
        if(NetUtil.isUsableLocalPort(15672)) {
            JOptionPane.showMessageDialog(null, "RabbitMQ 服务器未启动 ");
            System.exit(1);
        }
    }
}
