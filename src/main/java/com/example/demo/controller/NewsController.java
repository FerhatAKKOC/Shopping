package com.example.demo.controller;

import com.example.demo.constant.UserRoles;
import com.example.demo.entity.News;
import com.example.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/news")
public class NewsController {


    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @CrossOrigin
    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<News>> getAllNews() {
        try {
            List<News> newsList = newsService.getAllNews();
            if (newsList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(newsList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/{Id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<News> getNewsById(@PathVariable final Long Id) {

        News news = newsService.getNewsById(Id);

        if (news != null) {
            return new ResponseEntity<>(news, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @Secured(UserRoles.ROLE_ADMIN)
    @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<News> addNews(@RequestBody final News news) {
        try {
            News news1 = newsService.addNews(news);
            return new ResponseEntity<>(news1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @Secured(UserRoles.ROLE_ADMIN)
    @PostMapping(value = "/add/all", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> addNews(@RequestBody final List<News> newsList) {
        try {
            newsService.addNewsList(newsList);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @Secured(UserRoles.ROLE_ADMIN)
    @PutMapping(value = "/update/{Id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<News> updateNews(@PathVariable final Long Id, @RequestBody final News news) {

        try {
            if (newsService.isNewsExist(news.getId())) {
                var returned = newsService.updateNews(news);
                return new ResponseEntity<>(returned, HttpStatus.OK);
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
    public ResponseEntity<HttpStatus> deleteNewsById(@PathVariable final Long Id) {
        try {
            if (newsService.isNewsExist(Id)) {
                newsService.deleteNewsById(Id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @Secured(UserRoles.ROLE_ADMIN)
    @DeleteMapping(value = "/delete/all")
    public ResponseEntity<HttpStatus> deleteNewsById() {
        try {
            newsService.deleteAllNews();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
