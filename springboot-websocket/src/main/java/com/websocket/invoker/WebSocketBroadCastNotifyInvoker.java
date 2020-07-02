package com.websocket.invoker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author halo.l
 * @date 2020/7/2
 * @desc: websocket
 */
@ServerEndpoint("/notify")
@Component
@Slf4j
public class WebSocketBroadCastNotifyInvoker {

    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;

    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static Collection<WebSocketBroadCastNotifyInvoker> servers = Collections.synchronizedCollection(new ArrayList<>());

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        servers.add(this);
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("网络异常!!!!!!");
        }
        log.info("连接成功，当前在线" + servers.size() + "个用户");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose(){
        servers.remove(this);
        log.info("连接关闭，当前在线" + servers.size() + "个用户");
    }

    @OnError
    public void onError(Session session, Throwable error){
        log.info("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送自定义消息
     * */
    public void broadCast(String message) throws IOException {
        for (WebSocketBroadCastNotifyInvoker server : servers) {
            server.sendMessage(message);
        }
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
       this.session.getBasicRemote().sendText(message);
    }


}
