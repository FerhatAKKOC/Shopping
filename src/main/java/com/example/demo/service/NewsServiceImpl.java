package com.example.demo.service;

import com.example.demo.entity.News;
import com.example.demo.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsRepository newsRepository;

    @Override
    public News getNewsById(Long Id) {
        return newsRepository.getNewsById(Id);
    }

    @Override
    public boolean isNewsExist(Long Id) {
        return newsRepository.existsById(Id);
    }

    @Override
    public boolean isNewsExist(String head) {
        return newsRepository.existsByHead(head);
    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News addNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    public void addNewsList(List<News> newsList) {
        newsList.stream().filter(news -> !newsRepository.existsById(news.getId())).forEach(newsRepository::save);
    }

    @Override
    public News updateNews(News news) {
        var news_ = newsRepository.getNewsById(news.getId());
        news_.setHead(news.getHead());
        news_.setDescription(news.getDescription());
        news_.setImage(news.getImage());
        return newsRepository.save(news_);
    }

    @Override
    public void deleteAllNews() {
        newsRepository.deleteAll();
    }

    @Override
    public void deleteNewsById(Long Id) {
        newsRepository.deleteById(Id);
    }
}
