package com.websocket.controller;


import com.websocket.invoker.WebSocketServerInvoker;
import com.websocket.invoker.WebSocketBroadCastNotifyInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;

/**
 * WebSocketController
 * @author halo.l
 */
@RestController
public class WebSocketController {

    @Autowired
    private WebSocketBroadCastNotifyInvoker webSocketServerNotifyInvoker;

    @GetMapping("index")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("请求成功");
    }

    @GetMapping("page")
    public ModelAndView page(){
        return new ModelAndView("websocket");
    }

    @RequestMapping("/push/{toUserId}")
    public ResponseEntity<String> pushToWeb(String message, @PathVariable String toUserId) throws IOException {
        WebSocketServerInvoker.sendInfo(message,toUserId);
        return ResponseEntity.ok("MSG SEND SUCCESS");
    }

    @GetMapping("/pushMsg/{id}")
    public ResponseEntity<String> pushMsg(@PathVariable("id") String id) throws IOException {
        webSocketServerNotifyInvoker.broadCast(id + "号，服务器主动推送的消息来了");
        return ResponseEntity.ok("成功");
    }
}


