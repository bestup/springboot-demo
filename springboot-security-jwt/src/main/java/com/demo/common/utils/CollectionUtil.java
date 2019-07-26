package com.demo.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @description: 集合工具类
 */
public class CollectionUtil {

    /**
     * @param map 取值的集合
     * @param key 所想取值的集合的key
     * @return 返回key对应的value
     */
    public static String getMapValue(Map<String,Object> map,String key){
        String result = null;
        if(map != null){
            Iterator<String> iterable = map.keySet().iterator();
            while (iterable.hasNext()){
                Object object = iterable.next();
                if(key.equals(object))
                    if(map.get(object) != null)
                        result = map.get(object).toString();
            }
        }
        return result;
    }
    
    /** 将json格式封装的字符串数据转换成java中的Map数据
	 */
	public static Map<String, Object> JSON2Map(String text) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.parseObject(text);
		for (Entry<String, Object> entry : jsonObject.entrySet()) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

	/** 将json格式封装的字符串数据转换成java中的Map数据
	 */
	public static String Map2Json(Map<String, Object> map) {
		return JSON.toJSONString(map);
	}

}
