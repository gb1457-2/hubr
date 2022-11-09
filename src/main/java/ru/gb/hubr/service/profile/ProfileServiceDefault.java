package ru.gb.hubr.service.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.hubr.api.user.UserDto;
import ru.gb.hubr.api.user.profile.ProfileService;
import ru.gb.hubr.api.user.ProfileUserDto;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.service.mapper.UserMapper;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class ProfileServiceDefault implements ProfileService {

    private final AccountUserDao accountUserDao;
    private final UserMapper userMapper;

    ProfileUserDto profileUserDto = ProfileUserDto.builder()
            .email("vffsdf")
            .firstname("dfsdf")
            .lastname("dfsdf")
            .phone("dfsdfsdf")
            .username("system")
            .password("password")
            .build();

    @Transactional(readOnly = true)
    public UserDto findByUsername(String username) {

        return profileUserDto;

    }

    @Override
    public UserDto findById(Long idUser) {
        return profileUserDto;
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {

        return profileUserDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        LinkedList<UserDto> userDtos = new LinkedList<>();
        userDtos.add(profileUserDto);
        return userDtos;
    }



    @Override
    public UserDto getCurrentUser(HttpSession session) {
        return profileUserDto;
    }

}
