package com.demo.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtUtil {
	
	public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

	/** token秘钥*/
	public static final String SECRETKEY = "KJHUhjjJYgYUllVbXhKDHXhkSyHjlNiVkYzWTBac1Yxkjhuad";
	
	/** token 过期时间: 10天 */
	public static final int EXPIRE_DATE_TYPE = Calendar.DATE;
	public static final int EXPIRE_DATE = 10;
	 
	/**由字符串生成加密key*/
	public static SecretKey generalKey(String stringKey) {
		byte[] encodedKey = Base64.decodeBase64(stringKey);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}
 
	/** 创建jwt*/
	public static String createJWT(String id, String username) {
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		SecretKey key = generalKey(SECRETKEY);
		
		//create time
		//long nowMillis = System.currentTimeMillis();
		Date nowDate = new Date();
		
		// expire time
		Calendar expireTime = Calendar.getInstance();
		expireTime.add(EXPIRE_DATE_TYPE, EXPIRE_DATE);
		Date expireDate = expireTime.getTime();
		
		// header Map
		Map<String, Object> map = new HashMap<>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		
		//create token
		JwtBuilder builder = Jwts.builder()
			.setHeader(map)			//header
		    .setIssuer("")			//签发者(payload)
		    .setId(id)				//设置id
		    .setSubject(username) 	//需要存储的实体信息
		    .setIssuedAt(nowDate)	//签发时间
		    .setExpiration(expireDate)	//过期时间
		    .signWith(signatureAlgorithm, key);	//签名(signature)
		return builder.compact();
	}
	
	// 是否已过期
    public static boolean isExpiration(String expirationTime){
        /*return getTokenBody(token).getExpiration().before(new Date());*/

        //通过redis中的失效时间进行判断
        String currentTime = DateTimeUtil.getTime();
        if(DateTimeUtil.compareDate(currentTime,expirationTime)){
            //当前时间比过期时间小，失效
            return true;
        }else{
            return false;
        }
    }
 
	/** 解密jwt，获取实体 */
	public static Claims parseJWT(String jwt) throws ExpiredJwtException, UnsupportedJwtException,
			MalformedJwtException, SignatureException, IllegalArgumentException {
		SecretKey key = generalKey(SECRETKEY);
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
		return claims;
	}
	
	/** 根据Token获取user_id*/
	public static String getAppUID(String token) throws Exception {
		Claims claims = JwtUtil.parseJWT(token);
		if (null == claims.getId() || StringUtils.isEmpty(claims.getId())) {
			// token 校验失败, 抛出Token验证非法异常
			throw new Exception("token校验失败");
		}
		return claims.getId();
	}
	
	/** 根据Token获取username*/
	public static String getAppUserName(String token) throws Exception {
		Claims claims = JwtUtil.parseJWT(token);
		if (null == claims.getId() || StringUtils.isEmpty(claims.getSubject())) {
			// token 校验失败, 抛出Token验证非法异常
			throw new Exception("token校验失败");
		}
		return claims.getSubject();
	}
 
	/**
	* 实例演示
	*/
	public static void main(String[] args) {
		try {
			JSONObject subject = new JSONObject(true);
			subject.put("tem", "哈哈哈");
			subject.put("userName", "sixmonth");
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			System.out.println(uuid);
			String token = createJWT(uuid,"username");//10秒过期
			System.out.println(token);
			
			
			Claims claims = parseJWT(token);
			
			System.out.println("解析jwt："+claims.getSubject());
			
			JSONObject tem = JSONObject.parseObject(claims.getSubject());
			System.out.println(claims.getId());
			System.out.println("获取json对象内容："+tem.getString("userName"));	
			System.out.println(claims.getExpiration()+"///"+claims.getExpiration().getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
}
