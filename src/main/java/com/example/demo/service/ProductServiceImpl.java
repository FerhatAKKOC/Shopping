package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.model.ProductPage;
import com.example.demo.model.ProductSearchCriteria;
import com.example.demo.repository.ProductCriteriaRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCriteriaRepository productCriteriaRepository;

    @Override
    public Page<Product> getEmployees(ProductPage productPage, ProductSearchCriteria productPageCriteria) {
        return productCriteriaRepository.findAllWithFilters(productPage, productPageCriteria);
    }

    @Override
    public Product getProductById(Long Id) {
        return productRepository.getProductById(Id);
    }

    @Override
    public boolean isProductExist(Long Id) {
        return productRepository.existsById(Id);
    }

    @Override
    public boolean isProductExist(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void addProductList(List<Product> productList) {
        productList.stream().filter(news -> !productRepository.existsById(news.getId())).forEach(productRepository::save);
    }

    @Override
    public Product updateProduct(Product product) {
        var prod = productRepository.getProductById(product.getId());
        prod.setName(product.getName());
        prod.setPrice(product.getPrice());
        prod.setDescription(product.getDescription());
        prod.setDiscount(product.getDiscount());
        prod.setImage(product.getImage());
        return productRepository.save(prod);
    }

    @Override
    public void deleteById(Long Id) {
        productRepository.deleteById(Id);
    }

    @Override
    public List<Product> getProductsOverDiscount(Double discount) {
        return productRepository.getAllByDiscountGreaterThanEqual(discount);
    }

    @Override
    public List<Product> getProductsOverPriceAscOrder(Double price) {
        return productRepository.getAllByPriceGreaterThanEqualOrderByPriceAsc(price);
    }

    @Override
    public List<Product> getProductsOverPriceDescOrder(Double price) {
        return productRepository.getAllByPriceGreaterThanEqualOrderByPriceDesc(price);
    }
}
