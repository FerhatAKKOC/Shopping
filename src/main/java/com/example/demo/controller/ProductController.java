package com.example.demo.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.demo.constant.UserRoles;
import com.example.demo.entity.Product;
import com.example.demo.model.ProductPage;
import com.example.demo.model.ProductSearchCriteria;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /*
        Pagination and Filter, Search
        http://localhost:8080/products/
        http://localhost:8080/products/?page=2
        http://localhost:8080/products/?page=2&size=3
        http://localhost:8080/products/?name=Toy
        http://localhost:8080/products/?name=Toyota
        http://localhost:8080/products/?price=10000
        http://localhost:8080/products/?discount=20
     */
    @CrossOrigin
    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)     // returns all products
    public ResponseEntity<Page<Product>> getProducts(ProductPage productPage, ProductSearchCriteria productSearchCriteria) {

        return new ResponseEntity<>(productService.getEmployees(productPage, productSearchCriteria), HttpStatus.OK);
    }

/*    @CrossOrigin
    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)     // returns all products
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> productList = productService.getAllProducts();
            if (productList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(productList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @CrossOrigin
    @GetMapping(value = "/{Id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductById(@PathVariable final Long Id) {

        Product product = productService.getProductById(Id);

        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @Secured(UserRoles.ROLE_ADMIN)
    @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> addProduct(@RequestBody final Product product) {
        try {
            Product product_ = productService.addProduct(product);
            return new ResponseEntity<>(product_, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @Secured(UserRoles.ROLE_ADMIN)
    @PostMapping(value = "/add/all", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> addProduct(@RequestBody final List<Product> productList) {
        try {
            productService.addProductList(productList);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @Secured(UserRoles.ROLE_ADMIN)
    @PutMapping(value = "/update/{Id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProductById(@PathVariable final Long Id, @RequestBody final Product product) {

        try {
            if (productService.isProductExist(Id)) {
                var returnedProduct = productService.updateProduct(product);
                return new ResponseEntity<>(returnedProduct, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @Secured(UserRoles.ROLE_ADMIN)
    @DeleteMapping(value = "/delete/{Id}")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable final Long Id) {
        try {
            if (productService.isProductExist(Id)) {
                productService.deleteById(Id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
