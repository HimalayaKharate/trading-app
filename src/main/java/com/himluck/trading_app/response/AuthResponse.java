package com.himluck.trading_app.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private boolean status;
    private String message;
    public boolean isTwoFactorAuthEnabled;
    private String sessionId;

    public AuthResponse(String jwt, boolean b, String registerSuccess) {
        this(jwt, b, registerSuccess, false, null);
    }
}
