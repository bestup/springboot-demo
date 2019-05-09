package com.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.demo.entity.Goods;
import com.demo.repository.GoodsDao;
import com.demo.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService{
	
	@Autowired
	private GoodsDao goodsDao;

	@Override
	public List<Goods> findByName(String name) {
		return goodsDao.findByName(name);
	}

	@Override
	public List<Goods> findAll() {
		return goodsDao.findAll();
	}

	/**
	 * spring-data-jpa的分页和排序查询
	 * 多条件排序
	 */
	@Override
	public Page<Goods> findAllByPage(Integer page, Integer size) {

		//单个条件排序，直接这样写就行↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		//Sort sort = new Sort(Sort.Direction.DESC,"id"); 
		//Pageable pageable = PageRequest.of(page - 1,size,sort);
		
		//多个条件排序，将多个排序条件放进集合↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		List<Sort.Order> orders=new ArrayList<>();
		orders.add(new Sort.Order(Sort.Direction.DESC,"id")); 
		orders.add(new Sort.Order(Sort.Direction.DESC,"price")); 
		Pageable pageable = PageRequest.of(page - 1,size,Sort.by(orders));
		return goodsDao.findAll(pageable);
	}

	/**
	 * 分页加多条件排序加条件查询
	 */
	@Override
	public Page<Goods> findAllByPageCriteria(Integer page, Integer size, Goods goods) {
		List<Sort.Order> orders=new ArrayList<>();
		orders.add(new Sort.Order(Sort.Direction.DESC,"id")); 
		orders.add(new Sort.Order(Sort.Direction.DESC,"price")); 
		Pageable pageable = PageRequest.of(page - 1,size,Sort.by(orders));
		
		//封装查询对象Specification
		Specification<Goods> example = new Specification<Goods>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if(null != goods.getName()) {
					predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+goods.getName()+"%"));
				}
				if(0 != goods.getPrice()) {
					predicates.add(criteriaBuilder.equal(root.get("price"), goods.getPrice()));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return goodsDao.findAll(example,pageable);
	}

	@Override
	public Goods add(Goods goods) {
		return goodsDao.save(goods);
	}

	@Override
	public void delete(Goods goods) {
		goodsDao.delete(goods);
	}

	@Override
	public Goods update(Goods goods) {
		return goodsDao.save(goods);
	}
}
