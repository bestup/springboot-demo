package com.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="goods")
public class Goods implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * @Id	设置Table主键
	 * @Column（name = "自定义字段名"，length = "自定义长度"，nullable = "是否可以空"，unique = "是否唯一"，columnDefinition = "自定义该字段的类型和长度"）
	 * @GeneratedValue	设置主键的生成策略，这种方式依赖于具体的数据库，如果数据库不支持自增主键，那么这个类型是没法用的	IDENTITY：主键由数据库自动生成（主要是自动增长型） 
	 */
	@Id 	
	@Column(name="id", nullable=false,unique=true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="name", nullable=false,unique=true)
	private String name;
	
	@Column(name="type", nullable=false,unique=true)
	private String type;
	
	@Column(name="price", nullable=true,unique=false)
	private double price;
	
	/**
	 * @Transient  表示该属性并非一个到数据库表的字段的映射,ORM框架将忽略该属性. 如果一个属性并非数据库表的字段映射,就务必将其标示为 
	 */
	@Transient
	private String producer;
	
	public Goods() {
		super();
	}

	public Goods(Integer id, String name, String type, double price, String producer) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.producer = producer;
	}



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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	@Override
	public String toString() {
		return "Goods [id=" + id + ", name=" + name + ", type=" + type + ", price=" + price + ", producer=" + producer
				+ "]";
	}	
}
