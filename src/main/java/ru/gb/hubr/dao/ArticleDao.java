package ru.gb.hubr.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.Article;

public interface ArticleDao extends JpaRepository<Article, Long> {

    Page<Article> findAll(Pageable pageable);

}