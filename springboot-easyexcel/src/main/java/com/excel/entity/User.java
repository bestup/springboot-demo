package com.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
 * @author halo.l
 * @date 2020-01-10
 */
@Data
public class User {

    @ExcelProperty(value = {"用户" , "序号"} , index = 0)
    private Long id;

    @ExcelProperty(value = {"用户" , "用户名"} , index = 1)
    private String username;

    @ExcelProperty(value = {"用户" , "姓名"} , index = 2)
    private String name;

    @ExcelProperty(value = {"用户" , "昵称"} , index = 3)
    private String nickName;

    @ExcelProperty(value = {"用户" , "地址"} , index = 4)
    private String address;

    @ExcelProperty(value = {"用户" , "邮箱地址"} , index = 5)
    @ColumnWidth(40)
    private String email;

    @ExcelProperty(value = {"用户" , "用户手机"} , index = 6)
    @ColumnWidth(40)
    private String phone;

    @ExcelProperty(value = {"用户" , "创建时间"} , index = 7)
    @DateTimeFormat(value = "yyyy/MM/dd")
    @ColumnWidth(40)
    private Date createTime;

}
