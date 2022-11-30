package ru.gb.hubr.api.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.gb.hubr.api.dto.ArticleDto;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.entity.Article;
import ru.gb.hubr.entity.user.AccountUser;
import ru.gb.hubr.service.ArticleLikeService;
import ru.gb.hubr.service.CommentLikeService;

import java.util.NoSuchElementException;

import static java.util.Objects.nonNull;

@Mapper(uses = CommentMapper.class)
public interface ArticleMapper {

    Article toArticle(ArticleDto articleDto, @Context AccountUserDao accountUserDao);

    default AccountUser getAuthor(String author, @Context AccountUserDao accountUserDao) {
        return accountUserDao.findByUsername(author).orElseThrow(
                () -> new NoSuchElementException("There isn't author with name " + author));
    }

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "likesCount", ignore = true)
    @Mapping(target = "currentUserLikeId", ignore = true)
    ArticleDto toArticleDto(Article source,
                            @Context AccountUserDao accountUserDao,
                            @Context ArticleLikeService articleLikeService,
                            @Context String currentUserName,
                            @Context CommentLikeService commentLikeService);

    @AfterMapping
    default void articleComplete(@MappingTarget ArticleDto target,
                                 Article source,
                                 @Context AccountUserDao accountUserDao,
                                 @Context ArticleLikeService articleLikeService,
                                 @Context String currentUserName) {
        if (nonNull(source.getAuthor())) target.setAuthor(source.getAuthor().getUsername());
        target.setLikesCount(articleLikeService.countArticleLikeByArticleAndDeletedAtIsNull(source));
        if (nonNull(currentUserName)) {
            AccountUser currentUser = accountUserDao.findByUsername(currentUserName).orElse(null);
            target.setCurrentUserLikeId(articleLikeService.getCurrentUserLikeId(currentUser));
        }
    }



}
