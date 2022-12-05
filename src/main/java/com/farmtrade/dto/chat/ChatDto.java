package com.farmtrade.dto.chat;

import com.farmtrade.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDto {
    private User chatWith;
    private String lastMessage;
}
