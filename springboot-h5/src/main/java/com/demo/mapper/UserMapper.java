package com.demo.mapper;

import com.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 列表数据查询
     * @return
     */
    List<User> findByPage(@Param("nickname") String nickname);
}
