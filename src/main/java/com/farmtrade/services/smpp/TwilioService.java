package com.farmtrade.services.smpp;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.User;

public interface TwilioService {
    void sendVerificationMessage(User user, String activationCode);
    void sendWinnerMessage(OrderRequest orderRequest, User winner);
}
