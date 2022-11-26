package ru.gb.hubr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.hubr.dao.ArticleLikeDao;
import ru.gb.hubr.entity.Article;
import ru.gb.hubr.entity.ArticleLike;
import ru.gb.hubr.entity.user.AccountUser;

import java.util.Objects;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

    private final ArticleLikeDao articleLikeDao;

    private final AccountUserService accountUserService;

    public Long countArticleLikeByArticleAndDeletedAtIsNull(Article article) {
        return articleLikeDao.countArticleLikeByArticleAndDeletedAtIsNull(article);
    }

    public Long findCurrentUserLike(Long currentUserId) {
        AccountUser currentUser = accountUserService
                .findById(currentUserId);
        return getCurrentUserLikeId(currentUser);
    }

    public Long getCurrentUserLikeId(AccountUser accountUser) {
        if (isNull(accountUser)) return 0L;
        if (!isEmpty(accountUser.getArticleLikes())) {
            Optional<ArticleLike> articleLikeOptional = accountUser.getArticleLikes().stream()
                    .filter(articleLike -> isNull(articleLike.getDeletedAt())).findFirst();
            ArticleLike articleLike = articleLikeOptional.orElse(null);
            if (nonNull(articleLike)) return articleLike.getId();
        }
        return 0L;
    }

    @Transactional
    public void logicalDelete(Long articleLikeId, String currentUserName) {
        Long currentUserId = accountUserService.findByUsername(currentUserName).getId();
        ArticleLike articleLike = articleLikeDao.findById(articleLikeId).orElse(null);
        if (nonNull(articleLike) && (nonNull(articleLike.getAuthor()))
                && (Objects.equals(articleLike.getAuthor().getId(), currentUserId))) {
            articleLike.setDeletedAt(now());
            articleLikeDao.save(articleLike);
        }
    }

    public void add(Long articleId, String currentUserName) {
        Long currentUserId = accountUserService.findByUsername(currentUserName).getId();
        ArticleLike articleLike = new ArticleLike();
        Article article = new Article();
        article.setId(articleId);
        articleLike.setArticle(article);
        AccountUser accountUser = new AccountUser();
        accountUser.setId(currentUserId);
        articleLike.setAuthor(accountUser);
        articleLike.setCreatedAt(now());
        articleLikeDao.save(articleLike);
    }
}