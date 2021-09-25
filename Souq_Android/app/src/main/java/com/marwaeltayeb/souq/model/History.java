package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class History {

    @SerializedName("userId")
    private int userId;
    @SerializedName("productId")
    private int productId;

    public History(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }
}
