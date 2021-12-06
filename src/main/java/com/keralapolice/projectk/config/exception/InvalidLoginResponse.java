package com.keralapolice.projectk.config.exception;

public class InvalidLoginResponse {
    private String authStatus;


    public InvalidLoginResponse() {
       this.authStatus ="Your are not Authenticated";
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }
}
