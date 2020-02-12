package com.marwaeltayeb.souq.model;

public class LoginApiResponse {

    private int id;
    private String name;

    private String email;
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
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
