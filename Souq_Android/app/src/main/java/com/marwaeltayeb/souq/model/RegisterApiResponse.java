package com.marwaeltayeb.souq.model;

public class RegisterApiResponse {

    private boolean error;
    private String message;

    public RegisterApiResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
