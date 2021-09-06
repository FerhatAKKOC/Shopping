package com.example.demo.service;

import com.example.demo.entity.News;

import java.util.List;

public interface NewsService {

    List<News> getAllNews();

    News getNewsById(Long Id);

    boolean isNewsExist(Long Id);

    boolean isNewsExist(String head);

    News addNews(News news);

    void addNewsList(List<News> newsList);

    News updateNews(News news);

    void deleteAllNews();

    void deleteNewsById(Long Id);
}
