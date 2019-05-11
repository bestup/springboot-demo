package com.entity;

import io.swagger.annotations.ApiModel;

@ApiModel(value="Goods",description="商品类")
public class Goods {
	
	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + "]";
	}
	
	
}
