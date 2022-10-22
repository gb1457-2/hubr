package ru.gb.hubr.service.mapper;


import org.mapstruct.Mapper;
import ru.gb.hubr.api.event.EventDto;
import ru.gb.hubr.entity.EventUser;
import ru.gb.hubr.entity.TypeEvent;


@Mapper
public interface EventMapper {

    EventDto toEventDto(EventUser eventUser);

    EventUser toEventUser(EventDto eventDto);

    default TypeEvent getTypeEvent(String typeEvent) {
        return TypeEvent.valueOf(typeEvent);
    }

    default String getTypeEvent(TypeEvent typeEvent) {
        return typeEvent.name();
    }


}
