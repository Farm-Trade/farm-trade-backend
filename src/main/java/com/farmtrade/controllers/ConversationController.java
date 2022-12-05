package com.farmtrade.controllers;

import com.farmtrade.entities.Conversation;
import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.enums.OrderRequestStatus;
import com.farmtrade.filters.builders.OrderRequestSpecificationsBuilder;
import com.farmtrade.services.api.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.activation.UnsupportedDataTypeException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.farmtrade.constants.SwaggerConstants.SECURITY_SCHEME_NAME;

@RestController
@RequestMapping("api/conversations")
@SecurityRequirement(name = SECURITY_SCHEME_NAME)
@Tag(name = "Conversations Controller", description = "The Conversations API")
public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Operation(summary = "Get order request page")
    public List<Conversation> findPage() throws UnsupportedDataTypeException {
        return conversationService.findAll();
    }
}
