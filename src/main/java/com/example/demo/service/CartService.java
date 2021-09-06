package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.model.CartModel;

import java.util.List;

public interface CartService {

    CartModel updateQuantity(Long quantity, User user, Long productId) throws Exception;

    CartModel addProductToCart(Long Id, User user) throws Exception;

    void removeProductFromCart(Long Id, User user) throws Exception;

    List<CartModel> getAllCartProducts(User user);

    Double orderSummary(List<CartModel> cartList);
}
