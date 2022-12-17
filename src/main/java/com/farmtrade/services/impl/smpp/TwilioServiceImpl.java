package com.farmtrade.services.impl.smpp;

import com.farmtrade.entities.OrderRequest;
import com.farmtrade.entities.User;
import com.farmtrade.services.smpp.TwilioService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("application.yaml")
public class TwilioServiceImpl implements TwilioService {
    @Value("${twilio.accountSid}")
    private String twilioSid;
    @Value("${twilio.authToken}")
    private String authToken;
    @Value("${twilio.number}")
    private String twilioNumber;

    public void sendVerificationMessage(User user, String activationCode){
        String message = String.format(
                "%s, будьласка введіль номер верефікації: %s",
                user.getFullName(),
                activationCode
        );
        createMessage(user.getPhone(), message);
    }

    public void sendWinnerMessage(OrderRequest orderRequest, User winner){
        String message = String.format(
                "%s, вас вибрано як переможця в лоті з %s," +
                        " щодо подальшої співпраці може звязатись з власником лоту %s %s",
                winner.getFullName(),
                orderRequest.getProductName().getName(),
                orderRequest.getOwner().getFullName(),
                orderRequest.getOwner().getFullName()
        );
        createMessage(winner.getPhone(), message);
    }

    private void createMessage(String phone, String message) {
        Twilio.init(twilioSid, authToken);
        Message.creator(new PhoneNumber(phone), new PhoneNumber(twilioNumber), message).create();
    }
}
