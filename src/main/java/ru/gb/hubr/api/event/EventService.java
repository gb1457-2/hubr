package ru.gb.hubr.api.event;

import ru.gb.hubr.api.user.UserDto;

import java.util.List;

public interface EventService {
    List<EventDto> getEventUser(UserDto userDto);

    EventDto getEventByToken(String token);

    void deleteEventByToken(String token);
}
