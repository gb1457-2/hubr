package ru.gb.hubr.api.mapper;

import org.mapstruct.*;
import ru.gb.hubr.api.dto.UserNotificationDto;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.entity.UserNotification;
import ru.gb.hubr.entity.user.AccountUser;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Mapper(imports = LocalDateTime.class)
public interface UserNotificationMapper {

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "recipient", ignore = true)
    UserNotification toUserNotification(UserNotificationDto userNotificationDto, @Context AccountUserDao accountUserDao);


    @Mapping(target = "author", ignore = true)
    @Mapping(target = "recipient", ignore = true)
    UserNotificationDto toUserNotificationDto(UserNotification userNotification, @Context AccountUserDao accountUserDao);

    @AfterMapping
    default void userNotificationDtoComplete(@MappingTarget UserNotificationDto userNotificationDto, UserNotification userNotification,
                                                @Context AccountUserDao accountUserDao) {
        Long authorId = userNotification.getAuthor().getId();
        Long recipientId = userNotification.getRecipient().getId();
        String authorName = accountUserDao.findById(authorId).orElseThrow(
                () -> new NoSuchElementException("There isn't author with id " + authorId)
        ).getUsername();
        String recipientName = accountUserDao.findById(recipientId).orElseThrow(
                () -> new NoSuchElementException("There isn't author with id " + recipientId)
        ).getUsername();
        userNotificationDto.setAuthor(authorName);
        userNotificationDto.setRecipient(recipientName);
    }

    @AfterMapping
    default void userNotificationComplete(@MappingTarget UserNotification userNotification,
                                             UserNotificationDto userNotificationDto,
                                             @Context AccountUserDao accountUserDao) {
        AccountUser author = accountUserDao.findByUsername(userNotificationDto.getAuthor()).orElseThrow(
                () -> new NoSuchElementException("There isn't author with username " +
                        userNotificationDto.getAuthor())
        );
        AccountUser recipient = accountUserDao.findByUsername(userNotificationDto.getRecipient()).orElseThrow(
                () -> new NoSuchElementException("There isn't author with username " +
                        userNotificationDto.getRecipient())
        );
        userNotification.setAuthor(author);
        userNotification.setRecipient(recipient);
    }

}

