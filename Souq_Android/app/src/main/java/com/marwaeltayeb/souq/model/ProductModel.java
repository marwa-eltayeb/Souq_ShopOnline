package com.marwaeltayeb.souq.model;

public class ProductModel {

    private String productName;
    private double productPrice;
    private int productQuantity;
    private String productSupplier;
    private String productCategory;

    public ProductModel(String productName, double productPrice, int productQuantity, String productSupplier, String productCategory) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productSupplier = productSupplier;
        this.productCategory = productCategory;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public String getProductCategory() {
        return productCategory;
    }
}
