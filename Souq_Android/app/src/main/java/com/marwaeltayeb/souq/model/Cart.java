package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("userId")
    private int userId;
    @SerializedName("productId")
    private int productId;

    public Cart(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
