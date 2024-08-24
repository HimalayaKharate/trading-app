package com.himluck.trading_app.controller;

import com.himluck.trading_app.config.JwtProvider;
import com.himluck.trading_app.exceptions.UserAlreadyExistsException;
import com.himluck.trading_app.exceptions.UserDoesNotExist;
import com.himluck.trading_app.model.User;
import com.himluck.trading_app.repository.UserRepository;
import com.himluck.trading_app.response.AuthResponse;
import com.himluck.trading_app.service.CustomUserDetailService;
import com.himluck.trading_app.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user){
        User ifEmailExists = userRepository.findByEmail(user.getEmail());
        if(ifEmailExists != null){
            throw new UserAlreadyExistsException();
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullname(user.getFullname());
        User savedUser = userRepository.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse response = new AuthResponse(jwt, true, "register success");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user){

        String username = user.getEmail(), password = user.getPassword();

        Authentication auth = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        if(user.getAuth().isEnabled()){
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("tow factor auth is enabled");
            authResponse.setStatus(true);
            String otp = OtpUtils.generateOtp();
        }

        AuthResponse response = new AuthResponse(jwt, true, "login success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public Authentication authenticate(String username, String password){
        UserDetails details = customUserDetailService.loadUserByUsername(username);
        if(details == null){
            throw new UserDoesNotExist("email");
        }

        if(! password.equals(details.getPassword())){
            throw new UserDoesNotExist("password");
        }

        return new UsernamePasswordAuthenticationToken(details, password, details.getAuthorities());
    }
}
