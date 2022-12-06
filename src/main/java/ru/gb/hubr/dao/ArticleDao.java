package ru.gb.hubr.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.Article;
import ru.gb.hubr.entity.user.AccountUser;
import ru.gb.hubr.enumeration.ArticleTopic;

public interface ArticleDao extends JpaRepository<Article, Long> {

    Page<Article> findAll(Pageable pageable);
    Page<Article> findAllByTopic(Pageable pageable, ArticleTopic topic);
    Page<Article> findAllByAuthor(Pageable pageable, AccountUser author);

    Page<Article> findAllByTopicAndAuthor(Pageable pageable, ArticleTopic topic, AccountUser author);

}