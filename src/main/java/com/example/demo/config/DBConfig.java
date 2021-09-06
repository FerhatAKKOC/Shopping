package com.example.demo.config;

import com.example.demo.constant.UserRoles;
import com.example.demo.entity.News;
import com.example.demo.entity.Product;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.service.NewsService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//@Profile(Profiles.DEVELOPMENT)
@Component
public class DBConfig {

    private static final Logger logger = LoggerFactory.getLogger(DBConfig.class);

    @Value("classpath:data/products.json")
    private Resource resourceProduct;

    @Value("classpath:data/news.json")
    private Resource resourceNews;

    @Value("classpath:data/users.json")
    private Resource resourceUsers;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    NewsService newsService;

    @Autowired
    ObjectMapper objectMapper;

    /*
        Reads users&roles&passwords form "user.json" file and write them into db if not present.
    */
    @EventListener(ApplicationReadyEvent.class)
    public void generateUsersAndRoles() {
        Optional<User> user = userService.getUser("admin");

        if (!user.isPresent()) {

            Role roleAdmin = new Role(UserRoles.ROLE_ADMIN);
            Role roleCustomer = new Role(UserRoles.ROLE_CUSTOMER);

            if (resourceUsers.exists()) {
                try {
                    List<User> users = objectMapper.readValue(resourceUsers.getFile(), objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
                    List<User> userList = new ArrayList<>();
                    for (var usr : users) {

                        List<Role> userListRoles = new ArrayList<>();
                        for (var role : usr.getRoles()) {
                            if (role.getRole().equals(UserRoles.ROLE_ADMIN))
                                userListRoles.add(roleAdmin);
                            else if (role.getRole().equals(UserRoles.ROLE_CUSTOMER))
                                userListRoles.add(roleCustomer);
                        }
                        userList.add(new User(usr.getUsername(), usr.getPassword(), usr.isActive(), userListRoles));
                    }
                    userService.saveAllUsers(userList);

                } catch (Exception e) {
                    logger.error("Users were not read");
                    //throw new Exception("Products were not read");
                }
            } else {
                logger.error("File not FOUND: " + resourceUsers.getFilename());
            }
        }
    }

    /*
       Read products from "products.json" and write them into db if not present.
    */
    @EventListener(ApplicationReadyEvent.class)
    public void readProductsAndWriteDB() {

        if (resourceProduct.exists()) {
            try {
                List<Product> products = objectMapper.readValue(resourceProduct.getFile(), objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));

                for (var product : products)
                    if (!productService.isProductExist(product.getName()))
                        productService.addProduct(product);

            } catch (Exception e) {
                logger.error("Products were not read");
                //throw new Exception("Products were not read");
            }
        } else {
            logger.error("File not FOUND: " + resourceProduct.getFilename());
        }
    }

    /*
        Read News from "news.json" file and write them into db if not present.
    */
    @EventListener(ApplicationReadyEvent.class)
    public void readNewsAndWriteDB() {

        if (resourceNews.exists()) {
            try {
                List<News> newsList = objectMapper.readValue(resourceNews.getFile(), objectMapper.getTypeFactory().constructCollectionType(List.class, News.class));

                for (var news : newsList)
                    if (!newsService.isNewsExist(news.getHead()))
                        newsService.addNews(news);

            } catch (Exception e) {
                logger.error("News were not read");
                //throw new Exception("Products were not read");
            }
        } else {
            logger.error("File not FOUND: " + resourceNews.getFilename());
        }
    }
}
