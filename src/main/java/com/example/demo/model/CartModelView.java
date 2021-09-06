package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

public class CartModelView implements Serializable {

    private static final long serialVersionUID = -1569693073456549449L;

    private List<CartModel> cartModelList;

    Double orderSummary;

    public CartModelView() {
    }

    public CartModelView(List<CartModel> cartModelList, Double orderSummary) {
        this.cartModelList = cartModelList;
        this.orderSummary = orderSummary;
    }

    public List<CartModel> getCartModelList() {
        return cartModelList;
    }

    public void setCartModelList(List<CartModel> cartModelList) {
        this.cartModelList = cartModelList;
    }

    public Double getOrderSummary() {
        return orderSummary;
    }

    public void setOrderSummary(Double orderSummary) {
        this.orderSummary = orderSummary;
    }
}
