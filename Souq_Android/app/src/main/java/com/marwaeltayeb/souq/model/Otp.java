package com.marwaeltayeb.souq.model;

public class Otp {

    private String otp;
    private String email;
    private boolean error;
    private String message;

    public Otp(String message) {
        this.message = message;
        this.error = true;
    }

    public String getOtp() {
        return otp;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }
}
