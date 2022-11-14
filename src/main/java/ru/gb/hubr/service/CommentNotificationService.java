package ru.gb.hubr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.hubr.api.dto.CommentNotificationDto;
import ru.gb.hubr.api.mapper.CommentNotificationMapper;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.dao.CommentNotificationDao;
import ru.gb.hubr.entity.CommentNotification;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentNotificationService {

    private final CommentNotificationDao commentNotificationDao;
    private final AccountUserDao accountUserDao;
    private final CommentNotificationMapper commentNotificationMapper;

    public void save(CommentNotificationDto commentNotificationDto) {
        CommentNotification comment = commentNotificationMapper.toCommentNotification(commentNotificationDto, accountUserDao);
        commentNotificationDao.save(comment);
    }
}
