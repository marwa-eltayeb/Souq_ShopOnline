package com.marwaeltayeb.souq.model;

public class LoginApiResponse {

    private int id;
    private boolean error;
    private String message;
    private String token;

    public LoginApiResponse(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
