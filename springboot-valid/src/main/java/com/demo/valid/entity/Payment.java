package com.demo.valid.entity;

import com.demo.valid.annotation.EnumValid;
import com.demo.valid.annotation.ValidPatten;
import com.demo.valid.annotation.ValidVehicleNo;
import com.demo.valid.enums.PatternType;
import com.demo.valid.enums.PayType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Payment {

    @NotBlank(message = "id不能为空")
    private String id;

    @EnumValid(message = "类型有误", enumClass = PayType.class, method="isValidEnum",allowNull = false)
    private Integer product;

//    @ValidVehicleNo(message = "vehicleNo不能为空")
    @ValidPatten(message = "vehicleNo不能为空", patternType = PatternType.VEHICLENO)
    private String vehicleNo;

}
