package ru.gb.hubr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.hubr.api.dto.ArticleDto;
import ru.gb.hubr.api.mapper.ArticleMapper;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.dao.ArticleDao;
import ru.gb.hubr.entity.Article;
import ru.gb.hubr.entity.user.AccountUser;
import ru.gb.hubr.enumeration.ArticleTopic;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleDao articleDao;
    private final AccountUserDao accountUserDao;
    private final ArticleMapper articleMapper;

    public ArticleDto getArticleById(Long id){
        return articleMapper.toArticleDto(articleDao.findById(id).orElse(null), accountUserDao);
    }

    public List<ArticleDto> getAllArticles(){
        return articleDao.findAll(Sort.by("createdAt").ascending())
                .stream()
                .map(article -> articleMapper.toArticleDto(article, accountUserDao))
                .collect(Collectors.toList());
    }

    public ArticleDto saveArticle(ArticleDto articleDto){

        Article article = articleMapper.toArticle(articleDto, accountUserDao);
        return articleMapper.toArticleDto(articleDao.save(article), accountUserDao);
    }

    public Page<ArticleDto> getArticlesPage(Pageable pageable){

        return articleDao.findAll(pageable).map(article -> articleMapper.toArticleDto(article, accountUserDao));
    }

    public Page<ArticleDto> getArticlesPageByTopic(Pageable pageable, String topic){

        ArticleTopic articleTopic = ArticleTopic.getArticleTopicByEnumValue(topic);
        if (articleTopic != ArticleTopic.NOT_SELECTED){
            return articleDao.findAllByTopic(pageable, articleTopic)
                    .map(article -> articleMapper.toArticleDto(article, accountUserDao));
        } else {
            return articleDao.findAll(pageable).map(article -> articleMapper.toArticleDto(article, accountUserDao));
        }
    }

    public Page<ArticleDto> getArticlesPageByAuthor(Pageable pageable, String author){

        return articleDao.findAllByAuthor(pageable, articleMapper.getAuthor(author, accountUserDao))
                .map(article -> articleMapper.toArticleDto(article, accountUserDao));
    }

    public Page<ArticleDto> getArticlesPageByTopicAndAuthor(Pageable pageable, String topic, String author){

        ArticleTopic articleTopic = ArticleTopic.getArticleTopicByEnumValue(topic);
        if (articleTopic != ArticleTopic.NOT_SELECTED){
            return articleDao.findAllByTopicAndAuthor(pageable, articleTopic, articleMapper.getAuthor(author,accountUserDao))
                    .map(article -> articleMapper.toArticleDto(article, accountUserDao));
        } else {
            return articleDao.findAll(pageable).map(article -> articleMapper.toArticleDto(article, accountUserDao));
        }

    }
}
