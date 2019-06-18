package com.ocr.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @Description:图像识别service
 */
public interface ImgRecoService {
	
	Map<String,String> bankCard(InputStream is) throws IOException;

}
