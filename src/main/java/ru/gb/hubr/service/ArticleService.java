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

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleDao articleDao;

    private final ArticleLikeService articleLikeService;

    private final AccountUserDao accountUserDao;

    private final CommentLikeService commentLikeService;

    private final ArticleMapper articleMapper;

    public ArticleDto getArticleById(Long id, String currentUserName) {
        Article article = articleDao.findById(id).orElse(null);

        return articleMapper.toArticleDto(article, accountUserDao, articleLikeService,
                currentUserName, commentLikeService);
    }

    public List<ArticleDto> getAllArticles(String currentUserName) {
        return articleDao.findAll(Sort.by("createdAt").ascending())
                .stream()
                .map(article -> articleMapper.toArticleDto(article, accountUserDao, articleLikeService,
                        currentUserName, commentLikeService))
                .collect(Collectors.toList());
    }

    public ArticleDto saveArticle(ArticleDto articleDto, String currentUserName) {
        Article article = articleMapper.toArticle(articleDto, accountUserDao);
        return articleMapper.toArticleDto(articleDao.save(article), accountUserDao, articleLikeService,
                currentUserName, commentLikeService);
    }

    public Page<ArticleDto> getArticlesPage(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        return articleDao.findAll(pageable).map(article -> articleMapper.toArticleDto(article, accountUserDao,
                articleLikeService, null, commentLikeService));
    }
}
