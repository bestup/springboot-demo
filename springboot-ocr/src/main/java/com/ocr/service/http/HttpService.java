package com.ocr.service.http;

import java.util.List;

import org.apache.http.NameValuePair;

public interface HttpService {
	
	String doGet(String url);
	
	String doPost(String url, String content);
	
	String doPost(String url, List<NameValuePair> nvps); 

}
