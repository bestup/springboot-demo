package com.demo.valid.enums;

public enum PayType {

    prePay(1, "预付"), pay(2, "支付");

    private int code;
    private String name;

    private PayType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * @param name
     * @return
     */
    public static int getCode(String name) {
        for (PayType orderStatus : PayType.values()) {
            if (orderStatus.getName().equals(name)) {
                return orderStatus.code;
            }
        }
        return 0;
    }

    public static boolean isValidEnum(Integer value) {
        if (value == null) {
            return false;
        }
        for (PayType testTypeEnum : PayType.values()) {
            if (testTypeEnum.getCode() == value.intValue()) {
                return true;
            }
        }
        return false;
    }

}
