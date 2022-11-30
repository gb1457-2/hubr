package ru.gb.hubr.api.mapper;


import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.gb.hubr.api.dto.UserDto;
import ru.gb.hubr.entity.user.AccountUser;


@Mapper
public interface UserMapper {

    UserDto toUserDto(AccountUser accountUser);

    AccountUser toAccountUser(UserDto userDto);

    @AfterMapping
    default void UpdateResult(AccountUser accountUser, @MappingTarget UserDto userDto) {
        userDto.setLocked(accountUser.nowLocked());
    }

}
