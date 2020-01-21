package com.demo.model;

import java.util.Date;

/**
 * @author halo.l
 * @date 2020-01-21
 */
public class Student {

    /**学生id*/
    private int id;

    /**学生姓名*/
    private String name;

    /**学生性别 1：男 2：女*/
    private int sex;

    /**学生年龄*/
    private int age;

    /**学生学号*/
    private int student_no;

    /**学生出生年月*/
    private String birthday;

    /**学生创建时间*/
    private Date create_time;

    public Student(int id,String name,int sex,int age,int student_no,String birthday,Date create_time) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.student_no = student_no;
        this.birthday = birthday;
        this.create_time = create_time;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getStudent_no() {
        return student_no;
    }

    public void setStudent_no(int student_no) {
        this.student_no = student_no;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
