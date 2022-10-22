package ru.gb.hubr.api.user.profile;

import ru.gb.hubr.api.user.UserDto;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ProfileService {
     UserDto findByLogin(String login);

     UserDto save(final UserDto userDto);

     List<UserDto> findAll();



     UserDto getCurrentUser(HttpSession session);

}
