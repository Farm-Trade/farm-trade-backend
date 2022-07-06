package com.farmtrade.services.smpp;

import com.farmtrade.entities.User;
import com.farmtrade.utils.RandomUtil;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    public void sendVerificationMessage(User user, String activationCode){
        Twilio.init(System.getenv("TWILIO_SID"), System.getenv("TWILIO_TOKEN"));
        Message message = Message.creator(new PhoneNumber(user.getPhone()),
                new PhoneNumber("+16672260898"),
                String.format("%s, please enter your verification code: %s",
                        user.getFullName(), activationCode)).create();

    }
}
