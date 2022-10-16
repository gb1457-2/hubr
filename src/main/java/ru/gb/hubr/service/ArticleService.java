package ru.gb.hubr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.hubr.dao.ArticleDao;
import ru.gb.hubr.entity.Article;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleDao articleDao;

    public Article getArticleById(Long id){
        return articleDao.findById(id).orElse(null);
    }

    public List<Article> getAllArticles(){
        return articleDao.findAll();
    }

    public void saveArticle(Article article){

        articleDao.save(article);
    }
}
