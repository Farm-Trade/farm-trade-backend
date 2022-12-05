package com.farmtrade.services.api;

import com.farmtrade.dto.chat.ChatDto;
import com.farmtrade.dto.chat.MessageDto;
import com.farmtrade.entities.Conversation;
import com.farmtrade.entities.Message;

import java.util.List;
import java.util.Set;

public interface MessageService {
    Set<Message> saveMessage(MessageDto messageDto);
}
