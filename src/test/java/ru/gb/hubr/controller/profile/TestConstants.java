package ru.gb.hubr.controller.profile;

import ru.gb.hubr.api.dto.ProfileUserDto;

public class TestConstants {

    static final ProfileUserDto PROFILE_USER = ProfileUserDto.builder()
            .email("email")
            .firstName("firstName")
            .lastName("lastName")
            .password("password")
            .phone("phone")
            .username("username")
            .build();

}
