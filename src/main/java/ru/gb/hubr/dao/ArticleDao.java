package ru.gb.hubr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.Article;

public interface ArticleDao extends JpaRepository<Article, Long> {

}