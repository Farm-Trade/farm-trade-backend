package com.farmtrade.services.impl.api;

import com.farmtrade.dto.chat.ChatDto;
import com.farmtrade.dto.chat.MessageDto;
import com.farmtrade.entities.Conversation;
import com.farmtrade.entities.Message;
import com.farmtrade.entities.User;
import com.farmtrade.repositories.MessageRepository;
import com.farmtrade.services.api.ConversationService;
import com.farmtrade.services.api.MessageService;
import com.farmtrade.services.api.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ConversationService conversationService;
    private final UserService userService;

    public MessageServiceImpl(MessageRepository messageRepository, ConversationService conversationService, UserService userService) {
        this.messageRepository = messageRepository;
        this.conversationService = conversationService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Set<Message> saveMessage(MessageDto messageDto) {
        Conversation conversation = conversationService.findById(messageDto.getToConversation());
        User user = userService.getUser(messageDto.getFromUser());
        Message message = Message.builder()
                .text(messageDto.getText())
                .fromUser(user)
                .conversation(conversation)
                .build();
        messageRepository.save(message);
        return conversation.getMessages();
    }
}
