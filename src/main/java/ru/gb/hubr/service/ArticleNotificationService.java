package ru.gb.hubr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.hubr.api.dto.ArticleNotificationDto;
import ru.gb.hubr.api.mapper.ArticleNotificationMapper;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.dao.ArticleNotificationDao;
import ru.gb.hubr.entity.ArticleNotification;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleNotificationService {

    private final ArticleNotificationDao articleNotificationDao;
    private final AccountUserDao accountUserDao;
    private final ArticleNotificationMapper articleNotificationMapper;

    public void save(ArticleNotificationDto articleNotificationDto) {
        ArticleNotification article = articleNotificationMapper.toArticleNotification(articleNotificationDto, accountUserDao);
        articleNotificationDao.save(article);
    }
}
