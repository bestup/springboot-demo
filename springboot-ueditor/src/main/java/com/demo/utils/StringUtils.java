package com.demo.utils;

import java.util.UUID;

public class StringUtils {
	
	//获取UUID
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	//获取图片名称后缀格式 如.png
	public static String getImgExt(String oldName) {
		return oldName.substring(oldName.lastIndexOf("."));
	}
	
	
}
