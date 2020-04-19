package com.marwaeltayeb.souq.model;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("price")
    private double productPrice;
    @SerializedName("order_number")
    private String orderNumber;
    @SerializedName("order_date")
    private String orderDate;

    public double getProductPrice() {
        return productPrice;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }
}
