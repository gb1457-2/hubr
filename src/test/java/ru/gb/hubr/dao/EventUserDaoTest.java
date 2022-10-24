package ru.gb.hubr.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.gb.hubr.entity.EventUser;
import ru.gb.hubr.entity.TypeEvent;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@EnableJpaAuditing
class EventUserDaoTest {

    @Autowired
    EventUserDao eventUserDao;

    @Test
    public void saveTest() {

        EventUser save = eventUserDao.save(EventUser.builder().typeEvent(TypeEvent.CHANGE_EMAIL).build());
        UUID guid_event = save.getGuidEvent();
        save = eventUserDao.save(save);
        assertEquals(save.getDeletedAt(),save.getCreatedAt().plusSeconds(save.getLifetimeSeconds()));
        assertEquals(guid_event,save.getGuidEvent());
    }
}