package com.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author halo.l
 * 定义一个Excel模板
 */
@Data
@Accessors
public class User implements Serializable {

    @ExcelProperty(value="会员ID",index=0)
    private Integer id;

    @ExcelProperty(value="会员姓名",index=1)
    private String name;

    @ExcelProperty(value="会员年龄",index=2)
    private Integer age;

    @ExcelProperty(value="会员地址",index=3)
    private String address;

    @ExcelProperty(value="会员姓名",index=4)
    private String phone;

    @ExcelProperty(value="会员邮箱",index=5)
    private String email;
}
