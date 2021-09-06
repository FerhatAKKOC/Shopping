package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import com.example.demo.model.CartModel;
import com.example.demo.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {


    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductService productService;

    @Override
    public CartModel updateQuantity(Long quantity, User user, Long productId) throws Exception {

        var product = productService.getProductById(productId);

        if (product == null) throw new Exception("Product not Found");

        Cart cart = cartRepository.findByUserAndProduct(user, product);

        if (cart != null) {

            cart.setQuantity(quantity);
            cart = cartRepository.save(cart);
            return new CartModel(product, quantity, quantity * product.getPrice());

        } else {
            logger.warn("Cart is not found : Product: " + product.getName() + "  User:" + user.getUsername());
        }


        return new CartModel();
    }

    @Override
    public CartModel addProductToCart(Long Id, User user) throws Exception {

        var product = productService.getProductById(Id);

        if (product == null) throw new Exception("Product not Found");

        Cart cart = cartRepository.findByUserAndProduct(user, product);
        if (cart == null) {
            cart = new Cart();
            cart.setProduct(product);
            cart.setUser(user);
            cart.setQuantity(1L);
            cartRepository.save(cart);
            return new CartModel(product, 1L, product.getPrice());
        } else {
            logger.warn("Product has already been added : Product: " + product.getName() + "  User:" + user.getUsername());
        }

        return new CartModel(product, cart.getQuantity(), cart.getQuantity() * product.getPrice());
    }

    @Override
    public void removeProductFromCart(Long Id, User user) throws Exception {

        var product = productService.getProductById(Id);

        if (product == null) throw new Exception("Product not Found");

        Cart cart = cartRepository.findByUserAndProduct(user, product);

        if (cart != null)
            cartRepository.delete(cart);
        else
            logger.warn("Product has already been  removed : Product: " + product.getName() + "  User:" + user.getUsername());

    }

    @Override
    public List<CartModel> getAllCartProducts(User user) {

        List<Cart> listCart = cartRepository.findByUser(user);

        return listCart.stream()
                .map(cart -> new CartModel(cart.getProduct(), cart.getQuantity(), cart.getQuantity() * cart.getProduct().getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public Double orderSummary(List<CartModel> cartList) {
        return cartList.stream().map(cart -> cart.getPrice()).reduce(0d, Double::sum);
    }
}
