package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class Favorite {

    @SerializedName("userId")
    private int userId;
    @SerializedName("productId")
    private int productId;

    public Favorite(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
