package com.demo.common.enums;


/**
 * @description: 响应给前端返回的错误码枚举类
 */
public enum ResponseResultEnum {

    SUCCESS					(101,"成功"),
    FAILURE					(102,"失败"),
    USER_NEED_AUTHORITIES	(201,"用户未登录"),
    USER_LOGIN_FAILED		(202,"用户账号或密码错误"),
    USER_LOGIN_SUCCESS		(203,"用户登录成功"),
    USER_NO_ACCESS			(204,"用户无权访问"),
    USER_LOGOUT_SUCCESS		(205,"用户登出成功"),
    TOKEN_IS_BLACKLIST		(206,"此token为黑名单"),
    LOGIN_IS_OVERDUE		(207,"登录已失效")
    ;

    private Integer code;

    private String message;

    ResponseResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @deprecation:通过code返回枚举
    */
    public static ResponseResultEnum parse(int code){
    	ResponseResultEnum[] values = values();
        for (ResponseResultEnum value : values) {
            if(value.getCode() == code){
                return value;
            }
        }
        throw  new RuntimeException("Unknown code of ResultEnum");
    }
    
    public int getCode() {
		return code;
	}
    
    public String getMessage() {
		return message;
	}
    
}
