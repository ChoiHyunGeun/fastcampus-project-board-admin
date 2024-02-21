package com.fastcampus.projectboardadmin.controller;

import com.fastcampus.projectboardadmin.dto.socket.WebSocketMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/chat")
    public WebSocketMessage chat(WebSocketMessage message, Principal principal) {
        return WebSocketMessage.of("안녕하세요 " + principal.getName() + "님!" + message.content() + "라고 하셨나요?");
    }
}
