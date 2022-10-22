package ru.gb.hubr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.EventUser;

import java.util.List;
import java.util.UUID;

public interface EventUserDao extends JpaRepository<EventUser,Long> {
    List<EventUser> findByUserId(Long userId);

    EventUser findByGuidEvent(UUID guidEvent);

    void deleteByGuidEvent(UUID guidEvent);
}
