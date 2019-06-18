package com.ocr.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ocr.baidu.ai.api.utils.AuthService;
import com.ocr.baidu.ai.api.utils.Base64Util;
import com.ocr.baidu.ai.api.utils.FileUtil;
import com.ocr.baidu.ai.api.utils.HttpUtil;
import com.ocr.service.ImgRecoService;

import net.coobird.thumbnailator.Thumbnails;

@Controller
public class ImageController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ImgRecoService imgRecoService;
	
	@GetMapping("hello")
	public String hello() {
		return "hello.html";
	}
	
	//去图像上传页
	@GetMapping("addImg")
	public String addImg() {
		return "addImg";
	}
	
	//去图像识别页
	@GetMapping("toRecognition")
	public String toRecognition() {
		return "recognition";
	}
	
	/**
	 * @Description:上传图片并压缩保存
	 */
	@PostMapping("addImg")
	public String addImg(@RequestParam("pic") MultipartFile file) throws IllegalStateException, IOException {
		
		String oldName = file.getOriginalFilename();
		logger.info(oldName);	
		String path = "d:/";	
		try {
			String name = UUID.randomUUID().toString();
			String ext = oldName.substring(oldName.lastIndexOf("."));
			File putFile = new File(path,name+ext);
			Thumbnails.of(file.getInputStream()).size(100,180).toFile(putFile.getPath());
		} catch (IOException e) {
			e.getMessage();
		}
		return "index";
	}
	
	/**
	 * @throws IOException 
	 * @Description:银行卡图像识别
	 */
	@PostMapping("recognition")
	public String recognition(@RequestParam("pic") MultipartFile file) throws IOException {
		Map<String , String> result = imgRecoService.bankCard(file.getInputStream());
		logger.info("bankCard:"+result.get("bank_card_number"));
		return "recognition";
	}

}
