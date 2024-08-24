package com.himluck.trading_app.utils;

import java.util.Random;

public class OtpUtils {

    public static String generateOtp(){
        int otpLength = 6;
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < otpLength; i++){
            builder.append(random.nextInt('A', 'z'));
        }
        return builder.toString();
    }
}
