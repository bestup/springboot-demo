package com.demo.common.response;

import java.io.Serializable;

import com.demo.common.enums.ResponseResultEnum;

/**
 * @author: zzx
 * @date: 2018/10/15 15:00
 * @description: 返回的格式
 */

public final class ResponseData implements Serializable {

	private static final long serialVersionUID = 1725159680599612404L;

	private static final boolean SUCCESS = true;

	private static final boolean FAIL = false;
	
	private static final String SUCCESS_STATUS = "success";
	
	private static final String FAIL_STATUS = "fail";

	/** 登录成功返回  */
	public final static ResultData success(String message, String token) {
		ResultData resultData = new ResultData();
		resultData.put("code", ResponseResultEnum.SUCCESS.getCode());
		resultData.put("token", token);
		resultData.put("message", message);
		resultData.put("success", SUCCESS);
		resultData.put("status", SUCCESS_STATUS);
		return resultData;
	}

	/** 返回object，以及token 返回的msg，code为默认  */
	public final static ResultData success(String token) {
		ResultData resultData = new ResultData();
		resultData.put("code", ResponseResultEnum.SUCCESS.getCode());
		resultData.put("message", ResponseResultEnum.SUCCESS.getMessage());
		resultData.put("token", token);
		resultData.put("success", SUCCESS);
		resultData.put("status", SUCCESS_STATUS);
		return resultData;
	}

	public final static ResultData failure(int code, String message) {
		ResultData resultData = new ResultData();
		resultData.put("code", code);
		resultData.put("message", message);
		resultData.put("success", FAIL);
		resultData.put("status", FAIL_STATUS);
		return resultData;
	}

}
