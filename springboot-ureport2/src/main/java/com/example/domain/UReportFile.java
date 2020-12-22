package com.example.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UReportFile {

    private String id;

    private String name;

    private byte[] content;

    private Integer isDelete;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    public UReportFile(){

    }

    public UReportFile(String name, Date updateTime){
        this.name = name;
        this.updateTime = updateTime;
    }

}
