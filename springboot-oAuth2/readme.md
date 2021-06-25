两个项目，完整演示oauth2的请求授权流程（最简单的），简单的基理明白了，token的刷新等设计实现就很简单了。



通过appkey和appsecret获取accesstoken的也类似，通常情况下，这种很长一段数字字母的key和secret都是通过md5和sha1加密算法来生成的。





1.  appkey的生成

   > appkey生成比较简单，一般是用户ID+字符串组成，方法很多，做到唯一性就可以。

2. appsecret的生成

   > 原始数据可能是账号ID+注册时间+其他字符串，然后再通过md5或sha1加密来生成。而md5和sha1对比的话，md5比sha1更快，但sha1比md5强度更高，所以在此类授权应用中，通常都使用sha1算法，例如oauth的签名算法。

3. 验证授权

   > 例如要和微信通信里，要传递三个参数ABC，则需要将ABC与appsecret一起sha1一遍，得到一个签名串，然后将这个签名串与appkey一起传递给微信，
   > 此时，微信会通过appkey去查找对应的appsecret，然后再将ABC参数与查询出来的appsecret做一遍相同的签名算法，如果得到的签名串一致，则认为是授权成功。