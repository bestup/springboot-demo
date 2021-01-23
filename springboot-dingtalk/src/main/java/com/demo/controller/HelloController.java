package com.demo.controller;

import cn.snowheart.dingtalk.robot.starter.client.DingTalkRobotClient;
import cn.snowheart.dingtalk.robot.starter.entity.ActionCardButton;
import cn.snowheart.dingtalk.robot.starter.entity.DingTalkResponse;
import cn.snowheart.dingtalk.robot.starter.entity.MarkdownMessage;
import cn.snowheart.dingtalk.robot.starter.type.HideAvatarType;
import com.alibaba.fastjson.JSON;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RestController
@RequestMapping("/hello")
public class HelloController {

    public static String msg = "哈哈";


    @Autowired
    private DingTalkRobotClient dingTalkRobotClient;

    @GetMapping("/index")
    public String index() {
        return "hello index" + msg;
    }

    @GetMapping("/set")
    public String set(String msg) {
        this.msg = msg;
        return "set success";
    }

    /**
     * 发送普通文本消息
     * @return
     */
    @GetMapping("/text")
    public String text() {
        DingTalkResponse dingTalkResponse = dingTalkRobotClient.sendTextMessage("【测试】 测试内容", true);
        return JSON.toJSONString(dingTalkResponse);
    }

    /**
     * 发送markdown消息
     * @return
     */
    @GetMapping("/md")
    public String md() {
        String title = "杭州测试";
        String text = "#### 杭州天气\n";
        text = text + "> 9度，西北风1级，空气良89，相对温度73%\n";
        text = text + "> ![screenshot](https://img.alicdn.com/tfs/TB1NwmBEL9TBuNjy1zbXXXpepXa-2400-1218.png)\n";
        text = text + "> ###### 10点20分发布 [天气](https://www.dingtalk.com)";
        DingTalkResponse dingTalkResponse = dingTalkRobotClient.sendMarkdownMessage(title, text , true);
        return JSON.toJSONString(dingTalkResponse);
    }

    /**
     * 发送link消息
     * @return
     */
    @GetMapping("/link")
    public String link() {
        String title = "时代的火车向前开";
        String text = "这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林";
        String messageUrl = "https://blog.csdn.net/kh6417/article/details/110346565";
        String picUrl = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2311880584,2913270576&fm=26&gp=0.jpg";
        DingTalkResponse dingTalkResponse = dingTalkRobotClient.sendLinkMessage(title, text, messageUrl, picUrl);
        return JSON.toJSONString(dingTalkResponse);
    }

    /**
     * ActionCard
     */
    @GetMapping("/ac")
    public String ac() {
        String title = "乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身";
        String text = "![screenshot](https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2311880584,2913270576&fm=26&gp=0.jpg) \n" +
                " ### 乔布斯 20 年前想打造的苹果咖啡厅 \n" +
                " Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划";

        ActionCardButton actionCardButton = new ActionCardButton("点击阅读全文", "https://blog.csdn.net/kh6417/article/details/110346565");
        DingTalkResponse dingTalkResponse = dingTalkRobotClient.sendActionCardMessage(title, text, HideAvatarType.UNHIDE, actionCardButton);
        return JSON.toJSONString(dingTalkResponse);
    }






}
