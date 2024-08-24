package com.himluck.trading_app.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(){
        super("User already exists");
    }

}
