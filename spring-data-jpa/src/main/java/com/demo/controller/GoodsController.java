package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.Goods;
import com.demo.service.GoodsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "GoodsController")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	@GetMapping(value="test")
	@ApiOperation(value = "测试")
	public String test (Goods goods) {
		return "hello";
	}
	
	@GetMapping(value="findByName")
	@ApiOperation(value = "根据名称查询商品")
	public List<Goods> findByName(@RequestParam(name="name",required=true)String name) {
		return goodsService.findByName(name);
	}
	
	@GetMapping(value="findAll")
	@ApiOperation(value = "查询所有商品")
	public List<Goods> findAll() {
		return goodsService.findAll();
	}
	
	@GetMapping(value="findAllByPage")
	@ApiOperation(value = "商品信息分页查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "page", value = "当前页",required=true), @ApiImplicitParam(name = "size", value = "页大小",required=true) })
	public Page<Goods> findAllByPage(Integer page,Integer size) {
		return goodsService.findAllByPage(page,size);
	}
	
	/**
	 * 前台传参数，只要属性能匹配，会自动封装对象，不用加@RequestBody
	 */
	@PostMapping(value="findAllByPageAndCriteria")
	@ApiOperation(value = "按条件分页查询商品")
	public Page<Goods> findAllByPage(Goods goods,Integer page,Integer size) {
		return goodsService.findAllByPageCriteria(page,size,goods);
	}
	
	@PostMapping(value="add")
	@ApiOperation(value = "添加商品")
	public Goods add(Goods goods) {
		return goodsService.add(goods);
	}
	
	@DeleteMapping(value="delete")
	@ApiOperation(value = "删除商品")
	public void delete(Goods goods) {
		goodsService.delete(goods);
	}
	
	@PostMapping(value="update")
	@ApiOperation(value = "更新商品")
	public Goods update(Goods goods) {
		List<Goods> list = goodsService.findByName(goods.getName());
		Goods good = null;
		if(list.size()>0) {
			good = list.get(0);
		}
		good.setPrice(goods.getPrice());
		return goodsService.update(good);
	}

}
