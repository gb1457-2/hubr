package ru.gb.hubr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.gb.hubr.api.dto.UserNotificationDto;
import ru.gb.hubr.api.mapper.UserNotificationMapper;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.dao.UserNotificationDao;
import ru.gb.hubr.entity.UserNotification;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserNotificationService {

    private final UserNotificationDao userNotificationDao;
    private final UserNotificationMapper userNotificationMapper;
    private final AccountUserDao accountUserDao;


    public UserNotificationDto findUserNotificationById(Long id) {
        return userNotificationMapper.toUserNotificationDto(userNotificationDao.findById(id).orElse(null), accountUserDao);
    }

    public List<UserNotificationDto> findAll() {
        return userNotificationDao.findAll(Sort.by("createdAt").descending())
                .stream()
                .map(userNotification -> userNotificationMapper.toUserNotificationDto(userNotification, accountUserDao))
                .collect(Collectors.toList());
    }

    public void save(UserNotificationDto userNotificationDto) {
        UserNotification notification = userNotificationMapper.toUserNotification(userNotificationDto, accountUserDao);
                userNotificationDao.save(notification);
    }

}
