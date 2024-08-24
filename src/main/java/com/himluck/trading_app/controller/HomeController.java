package com.himluck.trading_app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping()
    public String Home(){
        return "Welcome to Home Page";
    }

    @GetMapping("/api/something")
    public String Secure(){
        return "Welcome to Home Page";
    }
}
