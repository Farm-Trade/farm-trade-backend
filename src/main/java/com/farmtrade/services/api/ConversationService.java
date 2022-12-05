package com.farmtrade.services.api;

import com.farmtrade.entities.Conversation;

import java.util.List;

public interface ConversationService {
    Conversation findById(Long id);

    List<Conversation> findAll();
}
