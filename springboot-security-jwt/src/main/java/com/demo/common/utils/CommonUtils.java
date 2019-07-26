package com.demo.common.utils;

import javax.servlet.http.HttpServletRequest;


public class CommonUtils {
	
	//判断是异步请求还是普通请求
	public static boolean isAjaxRequest(HttpServletRequest request) {
		if(request.getHeader( "content-type" ) == null && (request.getHeader("user-agent").toLowerCase().indexOf("mozilla")!=-1) ) {
			return false;
		}
		return true;
    }

}
