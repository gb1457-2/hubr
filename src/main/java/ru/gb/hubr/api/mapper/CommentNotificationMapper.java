package ru.gb.hubr.api.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.gb.hubr.api.dto.CommentNotificationDto;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.entity.AccountUser;
import ru.gb.hubr.entity.CommentNotification;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Mapper(imports = LocalDateTime.class)
public interface CommentNotificationMapper {

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "comment.id", source = "commentId")
    @Mapping(target = "complainType", source = "commentComplainType")
    CommentNotification toCommentNotification(CommentNotificationDto commentNotificationDto, @Context AccountUserDao accountUserDao);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "commentComplainType", source = "complainType")
    CommentNotificationDto toCommentNotificationDto(CommentNotification commentNotification, @Context AccountUserDao accountUserDao);

    @AfterMapping
    default void commentNotificationDtoComplete(@MappingTarget CommentNotificationDto commentNotificationDto, CommentNotification commentNotification,
                                                @Context AccountUserDao accountUserDao) {
        Long userId = commentNotification.getAuthor().getId();
        String userName = accountUserDao.findById(userId).orElseThrow(
                () -> new NoSuchElementException("There isn't author with id " + userId)
        ).getUsername();
        commentNotificationDto.setUsername(userName);
        commentNotificationDto.setUsername(commentNotification.getAuthor().getUsername());
    }

    @AfterMapping
    default void commentNotificationComplete(@MappingTarget CommentNotification commentNotification,
                                             CommentNotificationDto commentNotificationDto,
                                             @Context AccountUserDao accountUserDao) {
        AccountUser accountUser = accountUserDao.findByUsername(commentNotificationDto.getUsername()).orElseThrow(
                () -> new NoSuchElementException("There isn't author with username " +
                        commentNotificationDto.getUsername())
        );
        commentNotification.setAuthor(accountUser);
    }
}
