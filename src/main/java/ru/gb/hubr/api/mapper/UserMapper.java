package ru.gb.hubr.api.mapper;


import org.mapstruct.Mapper;
import ru.gb.hubr.api.dto.UserDto;
import ru.gb.hubr.entity.AccountUser;


@Mapper
public interface UserMapper {

    UserDto toUserDto(AccountUser accountUser);

    AccountUser toAccountUser(UserDto userDto);

}
