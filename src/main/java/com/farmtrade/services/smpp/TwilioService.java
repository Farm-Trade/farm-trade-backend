package com.farmtrade.services.smpp;

import com.farmtrade.entities.User;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("application.yaml")
public class TwilioService {
    @Value("${twilio.accountSid}")
    private String twilioSid;
    @Value("${twilio.authToken}")
    private String authToken;
    @Value("${twilio.number}")
    private String twilioNumber;

    public void sendVerificationMessage(User user, String activationCode){
        Twilio.init(twilioSid, authToken);
        Message.creator(new PhoneNumber(user.getPhone()),
                new PhoneNumber(twilioNumber),
                String.format("%s, please enter your verification code: %s",
                        user.getFullName(), activationCode)).create();

    }

}
