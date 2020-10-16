package com.demo.valid.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Shipper {

    @NotBlank(message = "货主名称不能为空")
    private String shipperName;

    @NotBlank(message = "货主id不能为空")
    private String shipperOrgId;

}
