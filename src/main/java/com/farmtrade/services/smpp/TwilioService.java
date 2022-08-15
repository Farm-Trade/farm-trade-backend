package com.farmtrade.services.smpp;

import com.farmtrade.entities.User;

public interface TwilioService {
    void sendVerificationMessage(User user, String activationCode);
}
