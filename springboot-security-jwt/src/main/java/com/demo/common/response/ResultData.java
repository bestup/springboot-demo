package com.demo.common.response;

import java.util.HashMap;

public class ResultData extends HashMap<Object, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResultData() {
	}
	
	public ResultData(String code, String status, String message) {
		this.put("code", code);
		this.put("status", status);
		this.put("message", message);
	}
}
