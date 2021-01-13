package com.demo.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class User {

    /**
     * 用户id
     */
    private String id;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 用户状态： 0启用 1停用
     */
    private Integer status;

    /**
     * 用户余额
     */
    private BigDecimal balance;

    /**
     * 创建时间
     */
    private Date createTime;

}
