package com.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.ueditor.ActionEnter;
import com.demo.utils.StringUtils;
@RestController
@RequestMapping("ueditor")
public class UeditorController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String imgSavePath = "D:/Program/nginx-1.10.3/html/images"; 
	
	private String prefixImg = "http://192.168.0.178:9990/images/";
	
	@RequestMapping(value="exec")
	public String exec(HttpServletRequest request) {
		String rootPath = request.getServletContext().getRealPath(File.separator);
		logger.info("使用ueditor插件");
		return new ActionEnter(request, rootPath).exec();
	}
	
	@RequestMapping(value = "/ueditorUploadImage")
	public Map<String, String> upload(@RequestParam("upfile") MultipartFile upfile) {
		
		logger.info("请求成功，图片名是："+upfile.getOriginalFilename());
		
		Map<String, String> map = 	new HashMap<>();	//定义map返回上传结果，ueditor需要
		
		String 		oldName 	= 	upfile.getOriginalFilename();
		String 		ext 		= 	StringUtils.getImgExt(oldName);
		String 		newName 	= 	StringUtils.getUUID() + ext;
		File 		file 		= 	new File(imgSavePath, newName);
		try {
			upfile.transferTo(file);
			
			//是否上传成功
			map.put("state", "SUCCESS");
			
			//图片title
			map.put("title", newName);
			
			//图片原名称，可以不写，亲测不写也没有关系
			//map.put("original", oldName);
			
			//图片后缀格式
			map.put("type", ext);
			
			//图片请求的路径
			map.put("url", prefixImg + newName);
			
			//图片的大小
			map.put("size", upfile.getSize() + "");
			
			return map;
		} catch (IOException e) {
			return map;
		}
	}
}
