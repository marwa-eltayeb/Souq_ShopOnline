package com.marwaeltayeb.souq.model;

import java.util.List;

public class ProductApiResponse {

    private List<ProductModel> products;

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }
}
