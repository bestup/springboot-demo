package com.export.excel.easyexcel.model;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ContentRowHeight 指定行号
 * @ColumnWidth 指定列宽
 * @DateTimeFormat 指定日期导出的格式
 * @NumberFormat  数字类型的格式
 */
@ContentRowHeight(15)
public class User {

    @ExcelProperty(value = "编号", index = 0)
    @ColumnWidth(15)
    private String id;

    @ExcelProperty(value = "姓名", index = 1)
    @ColumnWidth(15)
    private String name;

    @ExcelProperty(value = "手机号", index = 2)
    @ColumnWidth(16)
    private String phone;

    @ExcelProperty(value = "地址", index = 3)
    @ColumnWidth(15)
    private String address;

    @ExcelProperty(value = "年龄", index = 4)
    @ColumnWidth(15)
    private Integer age;

    @ExcelProperty(value = "生日", index = 5)
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ColumnWidth(30)
    private Date birthday;

    @ExcelProperty(value = "身高", index = 6)
    @ColumnWidth(15)
    @NumberFormat("#.##")
    private Double height;

    @ExcelProperty(value = "余额", index = 7)
    @ColumnWidth(15)
    @NumberFormat("#.###")
    private BigDecimal amount;

    @ExcelProperty(value = "是否是会员", index = 8,converter = IsAdminConverter.class)
    @ColumnWidth(20)
    private Integer isAdmin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }
}
