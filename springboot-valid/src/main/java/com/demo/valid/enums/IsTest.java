package com.demo.valid.enums;

public enum IsTest {
    DEV("1"), PROD("2");
    String code;
    IsTest(String code) {
        this.code = code;
    }
}
