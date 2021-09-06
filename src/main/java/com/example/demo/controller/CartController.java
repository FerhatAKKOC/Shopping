package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.model.CartModel;
import com.example.demo.model.CartModelView;
import com.example.demo.service.CartService;
import com.example.demo.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Autowired
    CustomUserDetailService customUserDetailService;

    @CrossOrigin
    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)     // returns all products
    public ResponseEntity<CartModelView> getAllCartProducts() {

        try {
            Optional<User> user = customUserDetailService.getCurrentUser();
            if (user.isPresent()) {

                List<CartModel> cartList = cartService.getAllCartProducts(user.get());
                if (cartList == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                CartModelView modelView = new CartModelView(cartList, cartService.orderSummary(cartList));
                return new ResponseEntity<CartModelView>(modelView, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/orderSummary", produces = APPLICATION_JSON_VALUE)     // returns all products
    public ResponseEntity<CartModelView> getOrderSummary() {

        try {
            Optional<User> user = customUserDetailService.getCurrentUser();
            if (user.isPresent()) {

                List<CartModel> cartList = cartService.getAllCartProducts(user.get());
                if (cartList == null) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                CartModelView modelView = new CartModelView(null, cartService.orderSummary(cartList));
                return new ResponseEntity<CartModelView>(modelView, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/add/{Id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CartModel> addProductToCart(@PathVariable final Long Id) {

        try {
            Optional<User> user = customUserDetailService.getCurrentUser();
            if (user.isPresent()) {
                CartModel cartModel = cartService.addProductToCart(Id, user.get());

                return new ResponseEntity<>(cartModel, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/update/{Id}/{Qty}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CartModel> updateQuantity(@PathVariable Map<String, String> pathVarsMap) {

        Long productId = Long.valueOf(pathVarsMap.get("Id"));   // productId
        Long quantity = Long.valueOf(pathVarsMap.get("Qty"));   // quantity

        try {
            Optional<User> user = customUserDetailService.getCurrentUser();
            if (user.isPresent()) {
                CartModel cartModel = cartService.updateQuantity(quantity, user.get(), productId);
                return new ResponseEntity<>(cartModel, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/delete/{Id}")
    public ResponseEntity<HttpStatus> removeProductFromCart(@PathVariable final Long Id) {
        try {
            Optional<User> user = customUserDetailService.getCurrentUser();
            if (user.isPresent()) {
                cartService.removeProductFromCart(Id, user.get());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
