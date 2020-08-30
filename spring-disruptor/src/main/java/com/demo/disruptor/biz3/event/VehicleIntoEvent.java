package com.demo.disruptor.biz3.event;

import lombok.Data;

/**
 * 定义车辆入场的事件
 */
@Data
public class VehicleIntoEvent {

    private String vehicleNo;
}
