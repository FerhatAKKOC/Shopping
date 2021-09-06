package com.example.demo.repository;

import com.example.demo.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    News getNewsById(Long Id);

    List<News> findAll();

    boolean existsById(Long Id);

    boolean existsByHead(String head);

    News save(News news);

    void deleteById(Long Id);

    void deleteAll();
}
