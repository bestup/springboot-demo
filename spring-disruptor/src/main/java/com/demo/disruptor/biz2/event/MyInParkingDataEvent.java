package com.demo.disruptor.biz2.event;

import lombok.Data;

@Data
public class MyInParkingDataEvent {

    /**
     * 车牌号
     */
    private String carLicense;
}
