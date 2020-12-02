package com.tool.util;
import com.alibaba.druid.filter.config.ConfigTools;

public class AliRSAUtil {

    /**
     * 阿里巴巴druid数据库jar进行获取公钥私钥
     * @param args
     */
    public static void main(String[] args) {

        String password = "jfmh123456..";
        String[] arr;
        try {
            arr = ConfigTools.genKeyPair(512);
            String privateKey = arr[0];
            System.out.println("privateKey:" + privateKey);
            String publicKey = arr[1];
            System.out.println("publicKey:" + publicKey);
            String pp = ConfigTools.encrypt(privateKey, password);
            System.out.println("password:" + pp);

            String ps = ConfigTools.decrypt(publicKey,
                    pp);
            System.out.println(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}