package com.example.mapper;

import com.example.domain.Delivery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeliveryMapper {

    List<Delivery> selectByPage(Delivery delivery);
}
