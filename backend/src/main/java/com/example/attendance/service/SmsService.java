package com.example.attendance.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private static final String ACCOUNT_SID = "AC883c360f828a1f4d2649a37accb13411";
    private static final String AUTH_TOKEN = "ccd1cdfc7d2bf052650042ab47a39f9a";
    private static final String FROM_PHONE = "+18566364816"; // Your Twilio number

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendSms(String toPhone, String message) {
        Message.creator(
                new PhoneNumber(toPhone),    // To number (e.g., +919876543210)
                new PhoneNumber(FROM_PHONE), // From number (your Twilio number)
                message
        ).create();
        System.out.println("SMS sent successfully to " + toPhone);
    }
}
