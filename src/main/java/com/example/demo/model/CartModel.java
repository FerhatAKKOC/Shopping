package com.example.demo.model;

import com.example.demo.entity.Product;

import java.io.Serializable;

public class CartModel implements Serializable {

    private static final long serialVersionUID = -8445356616328756488L;

    public Product product;

    private Long quantity;

    private Double price;

    public CartModel() {
    }

    public CartModel(Product product, Long quantity, Double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
