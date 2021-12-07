package com.keralapolice.projectk.config.exception;

public class InvalidLoginResponse {
    private String authStatus;


    public InvalidLoginResponse() {
       this.authStatus ="Authentication Failed";
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }
}
