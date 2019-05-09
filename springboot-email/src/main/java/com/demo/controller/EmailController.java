package com.demo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.EmailService;

@RestController
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	/**
	 * 发送普通邮件
	 */
	@PostMapping("sendMail")
	public void sendMail() {
		String to = "xxxx@qq.com";
		String subject = "测试";
		String content = "测试邮件";
		emailService.sendSimpleMail(to, subject, content);
	}
	
	/**
	 * 发送html页面的邮件
	 */
	@PostMapping("sendHtmlMail")
	public void sendHtmlMail() {
		String to = "xxxx@qq.com";
		String subject = "测试";
		String content = "";
		
		File path = null;
		try {
			//获取文件路径
			path = new File(ResourceUtils.getURL("classpath:").getPath()+"/templates/email.html");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			//使用FileUtils读取页面内容
			content = FileUtils.readFileToString(new File(path.getAbsolutePath()), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		emailService.sendHtmlMail(to, subject, content);
	}
	
	/**
	 * 发送带附件的邮件
	 */
	@PostMapping("sendAttachmentsMail")
	public void sendAttachmentsMail() {
		String to = "xxxx@qq.com";
		String subject = "测试";
		String content = "测试邮件";
		File file = null;
		try {
			file = new File(ResourceUtils.getURL("classpath:").getPath()+"/卸货.jpg");
		} catch (FileNotFoundException e) {
		}
		emailService.sendAttachmentsMail(to, subject, content,file.getAbsolutePath());
	}
	
	/**
     * 发送正文中有静态资源（图片）的邮件
     */
	@PostMapping("sendInlineResourceMail")
	public void sendInlineResourceMail() {
		File file = null;
		try {
			file = new File(ResourceUtils.getURL("classpath:").getPath()+"/卸货.jpg");
		} catch (FileNotFoundException e) {
			System.out.println("fail");
		}
		
		String imgId="123";
        String imgPath=file.getAbsolutePath();
        String content="<html><body>这是有图片的邮件：<img src=\'cid:"+imgId + "\'></body></html>"; 
 
		String to = "xxxx@qq.com";
		String subject = "测试";

		emailService.sendInlineResourceMail(to, subject, content,file.getAbsolutePath(),imgId);
	}
	
}
