package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product getProductById(Long Id);

    List<Product> getAllByDiscountGreaterThanEqual(Double discount);

    List<Product> getAllByPriceGreaterThanEqualOrderByPriceAsc(Double price);

    List<Product> getAllByPriceGreaterThanEqualOrderByPriceDesc(Double price);

    boolean existsById(Long Id);

    boolean existsByName(String name);

    List<Product> findAll();

    Product save(Product product);

    void deleteById(Long Id);
}
