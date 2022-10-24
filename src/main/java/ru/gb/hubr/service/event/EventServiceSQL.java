package ru.gb.hubr.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.hubr.api.event.EventDto;
import ru.gb.hubr.api.event.EventService;
import ru.gb.hubr.api.user.UserDto;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.dao.EventUserDao;
import ru.gb.hubr.entity.AccountUser;
import ru.gb.hubr.entity.EventUser;
import ru.gb.hubr.service.mapper.EventMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceSQL implements EventService {

    private final EventMapper eventMapper;
    private final EventUserDao eventUserDao;
    private final AccountUserDao accountUserDao;

    @Override
    public List<EventDto> getEventUser(UserDto userDto) {
        AccountUser accountUser = accountUserDao.findByLogin(userDto.getLogin()).orElseThrow();

        List<EventUser> listEvent = eventUserDao.findByUserId(accountUser.getId());
        return listEvent.stream()
                .filter(eventUser -> eventUser.getDeletedAt().isAfter(LocalDateTime.now()))
                .map(eventMapper::toEventDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto getEventByToken(String token) {
        EventUser byGuidEvent = eventUserDao.findByGuidEvent(UUID.fromString(token));
        if (byGuidEvent.getDeletedAt().isBefore(LocalDateTime.now())) {
            throw new NullPointerException();
        }
        return eventMapper.toEventDto(byGuidEvent);
    }

    @Override
    public void deleteEventByToken(String token) {
        eventUserDao.deleteByGuidEvent(java.util.UUID.fromString(token));
    }
}
