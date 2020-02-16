package com.marwaeltayeb.souq.model;

public class Favorite {

    private int userId;
    private int productId;

    public Favorite(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
