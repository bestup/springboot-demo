package com.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private Date createTime;

}
