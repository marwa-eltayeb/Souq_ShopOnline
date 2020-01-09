package com.marwaeltayeb.souq.model;

import java.util.List;

public class ProductApiResponse {

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
