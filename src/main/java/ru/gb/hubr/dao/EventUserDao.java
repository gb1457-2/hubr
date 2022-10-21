package ru.gb.hubr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.EventUser;

import java.util.List;

public interface EventUserDao extends JpaRepository<EventUser,Long> {
    List<EventUser> findByUserId(Long userId);

    EventUser findByGuid_event(String guidEvent);

    void deleteByGuid_event(String guidEvent);
}
