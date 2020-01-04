package com.marwaeltayeb.souq.model;

public class UserModel {

    private String name;
    private String email;
    private String password;
    private String token;

    public UserModel(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

}
