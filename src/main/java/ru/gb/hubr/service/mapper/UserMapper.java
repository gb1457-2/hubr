package ru.gb.hubr.service.mapper;


import org.mapstruct.Mapper;
import ru.gb.hubr.api.user.UserDto;
import ru.gb.hubr.entity.AccountUser;


@Mapper
public interface UserMapper {

    UserDto toUserDto(AccountUser accountUser);

    AccountUser toAccountUser(UserDto userDto);

}
