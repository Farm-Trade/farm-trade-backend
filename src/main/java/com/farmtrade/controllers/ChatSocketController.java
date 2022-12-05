package com.farmtrade.controllers;

import com.farmtrade.dto.chat.MessageDto;
import com.farmtrade.entities.Message;
import com.farmtrade.services.api.MessageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Set;

import static com.farmtrade.constants.SwaggerConstants.SECURITY_SCHEME_NAME;

@Controller
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
@Tag(name = "Message Controller", description = "The Messages web sockets")
public class ChatSocketController {
    private final MessageService messageService;

    public ChatSocketController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/chat/send")
    @SendTo("/topic/messages")
    public Set<Message> send(MessageDto message) {
        return messageService.saveMessage(message);
    }
}
