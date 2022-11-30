package ru.gb.hubr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.Article;
import ru.gb.hubr.entity.ArticleLike;

public interface ArticleLikeDao extends JpaRepository<ArticleLike, Long> {

    long countArticleLikeByArticleAndDeletedAtIsNull(Article article);
}