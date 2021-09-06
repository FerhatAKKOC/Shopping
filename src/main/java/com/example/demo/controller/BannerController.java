package com.example.demo.controller;

import com.example.demo.entity.News;
import com.example.demo.entity.Product;
import com.example.demo.service.NewsService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    NewsService newsService;

    @Autowired
    ProductService productService;

    @CrossOrigin
    @GetMapping(value = "/news/date", produces = APPLICATION_JSON_VALUE)
    public List<News> getAllNewsWithDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {

        //TODO fetch news after the time
        return new ArrayList<>();
    }

    @CrossOrigin
    @GetMapping(value = "/news/{Count}", produces = APPLICATION_JSON_VALUE)
    public List<News> getAllNewsWithCount(@PathVariable final Long Counts) {

        //TODO fetch last N Counts news
        return new ArrayList<>();
    }

    @CrossOrigin
    @GetMapping(value = "/products/discout/{Discount}", produces = APPLICATION_JSON_VALUE)     // returns all products
    public List<Product> getProductsWithDiscount(@PathVariable final Double Discount) {

        return productService.getProductsOverDiscount(Discount);
    }

    @CrossOrigin
    @GetMapping(value = "/products/price/{Price}/asc", produces = APPLICATION_JSON_VALUE)     // returns all products
    public List<Product> getProductsWitPriceAscOrder(@PathVariable final Double Price) {
        return productService.getProductsOverPriceAscOrder(Price);
    }

    @CrossOrigin
    @GetMapping(value = "/products/price/{Price}/desc", produces = APPLICATION_JSON_VALUE)     // returns all products
    public List<Product> getProductsWitPriceDescOrder(@PathVariable final Double Price) {
        return productService.getProductsOverPriceDescOrder(Price);
    }
}