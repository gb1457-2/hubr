package ru.gb.hubr.controller.profile;

import ru.gb.hubr.entity.user.AccountUser;

import java.time.LocalDateTime;

public class TestConstants {

    public static final AccountUser PROFILE_USER = AccountUser.builder()
            .email("email")
            .firstName("firstName")
            .lastName("lastName")
            .password("password")
            .phone("phone")
            .username("username")
            .build();

    public static final AccountUser BLOCKED_USER = AccountUser.builder()
            .email("email")
            .firstName("firstName")
            .lastName("lastName")
            .password("password")
            .phone("phone")
            .username("blocked_user")
            .lockedAt(LocalDateTime.now().minusHours(1))
            .lockedUntil(LocalDateTime.now().plusHours(1))
            .build();
}
