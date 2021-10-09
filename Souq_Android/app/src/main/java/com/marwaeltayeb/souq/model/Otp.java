package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class Otp {

    @SerializedName("otp")
    private String optCode;
    private String email;
    private boolean error;
    private String message;

    public Otp(String message) {
        this.message = message;
        this.error = true;
    }

    public String getOptCode() {
        return optCode;
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
