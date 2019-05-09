package com.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.entity.Goods;

public interface GoodsDao extends JpaRepository<Goods, Integer>  {
	
	/**
	 * @Modifying  自行写的sql需要加上 @Modifying 注解，因为默认是查询，所以增删改都要加 @Modifying,做查询不需要
	 * nativeQuery = true   ---->>>这个属性的意思是执行原生sql
	 */
	@Modifying
    @Query(value = "select * from goods where name = :name",nativeQuery = true)
	List<Goods> findByName(@Param("name")String name);

	/**
	 * 分页加多条件排序加条件查询
	 */
	Page<Goods> findAll(Specification<Goods> example, Pageable pageable);
}
