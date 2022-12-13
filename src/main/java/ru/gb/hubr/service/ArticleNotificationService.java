package ru.gb.hubr.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.hubr.api.dto.ArticleNotificationDto;
import ru.gb.hubr.api.mapper.ArticleNotificationMapper;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.dao.ArticleNotificationDao;
import ru.gb.hubr.entity.ArticleNotification;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleNotificationService {

    private final ArticleNotificationDao articleNotificationDao;
    private final AccountUserDao accountUserDao;
    private final ArticleNotificationMapper articleNotificationMapper;


    public ArticleNotificationDto findArticleNotificationById(Long id) {
        return articleNotificationMapper.toArticleNotificationDto(articleNotificationDao.findById(id).orElse(null), accountUserDao);
    }


    public List<ArticleNotificationDto> findAll() {
        return articleNotificationDao.findAll(Sort.by("createdAt").descending())
                .stream()
                .map(articleNotification -> articleNotificationMapper.toArticleNotificationDto(articleNotification, accountUserDao))
                .collect(Collectors.toList());
    }

    public void save(ArticleNotificationDto articleNotificationDto) {
        ArticleNotification article = articleNotificationMapper.toArticleNotification(articleNotificationDto, accountUserDao);
        articleNotificationDao.save(article);
    }
}
