package com.demo.service;

import java.util.List;
import org.springframework.data.domain.Page;

import com.demo.entity.Goods;

public interface GoodsService {
	
	List<Goods> findByName(String name);
	
	List<Goods> findAll();

	/**
	 * 分页加多条件排序查询
	 */
	Page<Goods> findAllByPage(Integer page,Integer size);
	
	/**
	 * 分页加多条件排序加条件查询
	 */
	Page<Goods> findAllByPageCriteria(Integer page,Integer size,Goods goods);
	
	Goods add(Goods goods);
	
	Goods update(Goods goods);
	
	void delete(Goods goods);
	
}
