package com.example.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @JsonIgnore
    private String code;

    private Integer type;

    private String selfCode;

    private String vehicleNo;

    private String goodsId;

    private String goodsCode;

    @JsonIgnore
    private Integer status;

    private Integer weightStatus;

    @JsonIgnore
    private BigDecimal freight;

    @JsonIgnore
    private BigDecimal goodsPrice;

    @JsonIgnore
    private BigDecimal goodsAmount;

    private String driverName;

    private String driverPhone;

    @JsonIgnore
    private String supId;

    private String supName;

    @JsonIgnore
    private String carrierOrgId;

    private String carrierOrgName;

    @JsonIgnore
    private Integer isNetworkTransportation;

    @JsonIgnore
    private String startProvinceId;

    @JsonIgnore
    private String startProvinceName;

    @JsonIgnore
    private String startCityId;

    @JsonIgnore
    private String startCityName;

    @JsonIgnore
    private String startCountryId;

    @JsonIgnore
    private String startCountryName;

    @JsonIgnore
    private String startStationId;

    private String startStationName;

    @JsonIgnore
    private String endProvinceId;

    @JsonIgnore
    private String endProvinceName;

    @JsonIgnore
    private String endCityId;

    @JsonIgnore
    private String endCityName;

    @JsonIgnore
    private String endCountryId;

    @JsonIgnore
    private String endCountryName;

    @JsonIgnore
    private String endStationId;

    private String endStationName;

    private BigDecimal loadGrossWeight;

    private BigDecimal loadTareWeight;

    private BigDecimal loadNetWeight;

    private BigDecimal unloadGrossWeight;

    private BigDecimal unloadTareWeight;

    private BigDecimal unloadNetWeight;

    @JsonIgnore
    private BigDecimal discountNetWeight;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private Date loadTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private Date unloadTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private Date loadGrossTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private Date loadTareTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private Date unloadGrossTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private Date unloadTareTime;

    @JsonIgnore
    private String goodsCategoryId;

    private String goodsCategoryName;

    @JsonIgnore
    private String goodsDetailId;

    private String goodsDetailName;

    private Integer isFieldAudit;

    @JsonIgnore
    private String fieldAuditUserId;

    private String fieldAuditUserName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    private Date fieldAuditTime;

    private Integer fieldAuditStatus;

    private String dispatchingUserId;

    private String dispatchingUserName;

    @JsonIgnore
    private String fieldId;

    private String fieldName;

    private String remarks;

}