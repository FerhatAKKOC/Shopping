package com.example.demo.model;

/*
 * Used for Searching&Filtering in DB. URL search params map tı this class object in request
 * */
public class ProductSearchCriteria {

    private String name;
    private Double price;
    private Double discount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
