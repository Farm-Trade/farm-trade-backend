package com.farmtrade.services.impl.api;

import com.farmtrade.entities.Conversation;
import com.farmtrade.entities.User;
import com.farmtrade.exceptions.EntityNotFoundException;
import com.farmtrade.repositories.ConversationRepository;
import com.farmtrade.services.api.ConversationService;
import com.farmtrade.services.security.AuthService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {
    private final ConversationRepository conversationRepository;
    private final AuthService authService;

    public ConversationServiceImpl(ConversationRepository conversationRepository, AuthService authService) {
        this.conversationRepository = conversationRepository;
        this.authService = authService;
    }

    @Override
    public Conversation findById(Long id) {
        return conversationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Conversation.class, id));
    }

    @Override
    public List<Conversation> findAll() {
        User user = authService.getUserFromContext();
        return null;
    }
}
