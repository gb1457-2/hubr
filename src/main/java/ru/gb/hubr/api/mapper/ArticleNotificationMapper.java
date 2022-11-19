package ru.gb.hubr.api.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.gb.hubr.api.dto.ArticleNotificationDto;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.entity.ArticleNotification;
import ru.gb.hubr.entity.user.AccountUser;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Mapper(imports = LocalDateTime.class)
public interface ArticleNotificationMapper {

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "article.id", source = "articleId")
    @Mapping(target = "complainType", source = "articleComplainType")
    ArticleNotification toArticleNotification(ArticleNotificationDto articleNotificationDto, @Context AccountUserDao accountUserDao);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "articleComplainType", source = "complainType")
    ArticleNotificationDto toArticleNotificationDto(ArticleNotification articleNotification, @Context AccountUserDao accountUserDao);

    @AfterMapping
    default void articleNotificationDtoComplete(@MappingTarget ArticleNotificationDto articleNotificationDto, ArticleNotification articleNotification,
                                                @Context AccountUserDao accountUserDao) {
        Long userId = articleNotification.getAuthor().getId();
        String userName = accountUserDao.findById(userId).orElseThrow(
                () -> new NoSuchElementException("There isn't author with id " + userId)
        ).getUsername();
        articleNotificationDto.setUsername(userName);
        articleNotificationDto.setUsername(articleNotification.getAuthor().getUsername());
    }

    @AfterMapping
    default void articleNotificationComplete(@MappingTarget ArticleNotification articleNotification,
                                             ArticleNotificationDto articleNotificationDto,
                                             @Context AccountUserDao accountUserDao) {
        AccountUser accountUser = accountUserDao.findByUsername(articleNotificationDto.getUsername()).orElseThrow(
                () -> new NoSuchElementException("There isn't author with username " +
                        articleNotificationDto.getUsername())
        );
        articleNotification.setAuthor(accountUser);
    }
}
