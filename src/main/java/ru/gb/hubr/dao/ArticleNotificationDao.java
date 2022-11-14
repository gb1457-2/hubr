package ru.gb.hubr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.ArticleNotification;

public interface ArticleNotificationDao extends JpaRepository<ArticleNotification, Long> {

}