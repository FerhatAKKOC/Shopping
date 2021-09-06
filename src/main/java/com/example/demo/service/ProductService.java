package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.model.ProductPage;
import com.example.demo.model.ProductSearchCriteria;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Page<Product> getEmployees(ProductPage productPage, ProductSearchCriteria productPageCriteria);

    Product getProductById(Long Id);

    boolean isProductExist (Long Id);

    boolean isProductExist(String name);

    List<Product> getAllProducts();

    Product addProduct(Product product);

    void addProductList(List<Product> productList);

    Product updateProduct(Product product);

    void deleteById(Long Id);

    List<Product> getProductsOverDiscount(Double discount);

    List<Product> getProductsOverPriceAscOrder(Double price);

    List<Product> getProductsOverPriceDescOrder(Double price);
}
