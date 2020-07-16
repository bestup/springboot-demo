package com.tool.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author tanglong
 * @date 2019-11-23
 * 非对称加密算法
 * 后台用私钥加密token， 微服务端用公钥解密token
 */
public class RSAUtil {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 获取密钥对
     * @return 密钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     * @param data 待加密数据
     * @return
     */
    public static String encrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64String(encryptedData));
    }

    /**
     * RSA解密
     *
     * @param data 待解密数据
     * @return
     */
    public static String decrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] dataBytes = Base64.decodeBase64(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 签名
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * 验签
     * @param srcData 原始字符串
     * @param publicKey 公钥
     * @param sign 签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

    public static void main(String[] args) {
        try {
            // 生成密钥对
            //KeyPair keyPair = getKeyPair();
            //String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
            //String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));

            String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ1rI8jxH9aRIaQbOiWCxMWso5WqssesmGyIYcWQWB2mbdhbPJsO5NZZ2hF4Fr0jgsYecIwHFZhb6ZPJElmk0nDPp89mW9ZuPFQx3P4YIP7EO9oWHmPpO5G77y9IzfQFdVg/TuI0sifzDz0Hp4zgENXPNw2L8/cJBL/HCZLO1YZdAgMBAAECgYBnMQGfSrlVPYQxAIwoZMW600XU6bYSgIjWtLxq9wlcPmpJZt2ew6+s9++kffVh/TJPz0p18Hruimner/NwgkRUF0EiNo6En/XnsJVJm+l3KRwgKBST9bFKLqp9t1yHJj/zdGOtoF9uio4GRfNghQCUCCdmMbd1qlmKk00ISCiGAQJBAOgrKiQp9e+O7gN6c+NSkWqpKPHMPx1zBQb1U6nHfd0cM3bfYuUluRHIbbIkh/sjjGCB9WA4eh1/5jFcvFMAgoECQQCtk7i3WSgo2q1JJwPtaZQSxIrfZVxll8VYstNbaggXm+0///uEgUE3keYgf2MpY1aY5M+oB+6tKGwIB1UB/F3dAkBAjlgObDh9XyHhh7gXmorivJys910JZEUelTKpP9ZImCHbSOwnyWB3iGKwKSZCd7T91MUU9z9FWp34Vc/ryHWBAkAlCYBAnyWSkhanpUuRPRX7azS2tkog42GYq4+t0dIoN31ISrbAb1QbJ2aTSJtAGZJAzZIBqK+lAxsKG4ZoBhGBAkBSBTdWnEpQB39+1FUyPkQ8oAG69X7VeeaSgZ36YRQ9dVTpjkSWwjiVta4c9VyiH6uTT4dxdlcnECE1AZa3C2jH";
            String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdayPI8R/WkSGkGzolgsTFrKOVqrLHrJhsiGHFkFgdpm3YWzybDuTWWdoReBa9I4LGHnCMBxWYW+mTyRJZpNJwz6fPZlvWbjxUMdz+GCD+xDvaFh5j6TuRu+8vSM30BXVYP07iNLIn8w89B6eM4BDVzzcNi/P3CQS/xwmSztWGXQIDAQAB";

            // RSA加密token
            String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhZG1pbiIsImp0aSI6Ik1JR2ZNQTBHQ1NxR1NJYjNEUUVCQVFVQUE0R05HU0liM0RRRUJBUVVBQTRHTkFEQ0JpUUtCIiwic3ViIjoidXNlcmFkc2ZzZiIsImlhdCI6MTU3MzY1MDA0NiwiZXhwIjoxNTc2MjQyMDQ2fQ.KVv_PUCrGTHEmE3_IeSKLR9oVDYH3Q97kibf1ZzC10c";

            String encryptData = encrypt(token, getPrivateKey(privateKey));
            System.out.println("加密后内容:" + encryptData);
            System.out.println("加密后内容长度:" + encryptData.length());

            // RSA解密
            String decryptData = decrypt(encryptData, getPublicKey(publicKey));
            System.out.println("解密后内容:" + decryptData);
            System.out.println("解密后内容长度:" + decryptData.length());

            // RSA签名
            String sign = sign(token, getPrivateKey(privateKey));

            // RSA验签
            boolean result = verify(token, getPublicKey(publicKey), sign);
            System.out.println("验签结果:" + result);

            System.out.print("签名:" + sign);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }
    }

}
