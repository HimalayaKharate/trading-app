package com.himluck.trading_app.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class UserDoesNotExist extends BadCredentialsException {
    public UserDoesNotExist(String message){
        super("user does not exist - invalid credentials provided " + message);
    }
}
