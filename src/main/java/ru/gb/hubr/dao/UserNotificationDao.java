package ru.gb.hubr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.UserNotification;

public interface UserNotificationDao  extends JpaRepository<UserNotification, Long> {
}
