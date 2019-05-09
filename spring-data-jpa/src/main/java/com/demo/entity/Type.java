package com.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="type")
public class Type implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id 	
	@Column(name="id", nullable=false,unique=true)
	@GeneratedValue
	private Integer id;
	
	@Column(name="name", nullable=false,unique=true)
	private Integer name;

	public Type() {
		super();
	}

	public Type(Integer id, Integer name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Type [id=" + id + ", name=" + name + "]";
	}
	
}
