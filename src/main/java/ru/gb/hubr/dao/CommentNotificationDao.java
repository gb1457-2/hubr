package ru.gb.hubr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.CommentNotification;

public interface CommentNotificationDao extends JpaRepository<CommentNotification, Long> {

}