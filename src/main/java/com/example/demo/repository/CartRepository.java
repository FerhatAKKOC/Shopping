package com.example.demo.repository;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser(User user);

    void deleteByUserAndProduct(User user, Product product);

    Cart findByUserAndProduct(User user, Product product);

    Cart save(Cart cart);

    void delete(Cart cart);
}
