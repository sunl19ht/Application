package com.sunl19ht.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.sunl19ht.pojo.Message;
import com.sunl19ht.pojo.Result;
import com.sunl19ht.pojo.User;
import com.sunl19ht.service.UserService;
import com.sunl19ht.task.WebSocketServer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("msg")
public class ChatController {
//    @MessageMapping("/chat")
//    @SendTo("/topic/messages")
//    public Message sendMessage(Message message) {
//        log.info(message.toString());
//        return message;
//    }
    @Autowired
    private WebSocketServer webSocketServer;
    @PostMapping("/send")
    public Result<String> login(@RequestBody Message message) {
        log.info(message.toString());
        try {
            // 解析 Token，验证签名
            Jwts.parser()
                    .setSigningKey("userToken")
                    .parseClaimsJws(message.getToken())
                    .getBody();
        } catch (SignatureException e) {
            // 签名验证失败，可以根据实际情况处理
            System.err.println("Invalid token signature");
            return Result.success("Invalid token signature");
        }
        webSocketServer.sendToAllClient(message.getSender() + "说: " + message.getContent() );
        return Result.success();
    }
}
