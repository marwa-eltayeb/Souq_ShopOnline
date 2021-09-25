package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class Ordering {

    @SerializedName("name_on_card")
    private String nameOnCard;
    @SerializedName("card_number")
    private String cardNumber;
    @SerializedName("expiration_date")
    private String fullDate;
    @SerializedName("userId")
    private int userId;
    @SerializedName("productId")
    private int productId;

    public Ordering(String nameOnCard, String cardNumber, String fullDate, int userId, int productId) {
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.fullDate = fullDate;
        this.userId = userId;
        this.productId = productId;
    }
}



