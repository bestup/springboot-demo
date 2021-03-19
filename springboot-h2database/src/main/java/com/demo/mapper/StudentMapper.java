package com.demo.mapper;

import com.demo.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface StudentMapper {

    @Insert({"insert into test.student(`name`,age,address,score,create_time) values(#{name},#{age},#{address},#{score},#{createTime})"})
    Integer insert(Student student);

}
