package com.ocr.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ocr.baidu.ai.api.utils.AuthService;
import com.ocr.baidu.ai.api.utils.Base64Util;
import com.ocr.baidu.ai.api.utils.FileUtil;
import com.ocr.baidu.ai.api.utils.HttpUtil;
import com.ocr.service.ImgRecoService;

@Service
public class ImgRecoServiceImpl implements ImgRecoService{

	@Override
	public Map<String, String> bankCard(InputStream is) throws IOException {
		
		// 银行卡识别url
        String bankcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/bankcard";
        try {
            byte[] imgData = FileUtil.readFileByBytes(is);
            String imgStr = Base64Util.encode(imgData);
            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = AuthService.getAuth();
            String result = HttpUtil.post(bankcardIdentificate, accessToken, params);
            JSONObject jsonObject = JSON.parseObject(result);
            String json = jsonObject.getString("result");
            Map<String ,String> mapss = JSON.parseObject(json,Map.class);
            return mapss;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
	

}
