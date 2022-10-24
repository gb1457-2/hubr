package ru.gb.hubr.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypeEvent {
    CHANGE_PASSWORD("Пароль изменен", 60 * 60 * 24),
    DELETE_PROFILE("Удаление учетной записи", 60 * 60 * 24),
    CHANGE_EMAIL("Электронная почта изменена", 60 * 60 * 24),
    RESET_PASSWORD("Смена пароль", 60 * 60 * 24),
    RESET_EMAIL("Смена электронной почты", 60 * 60 * 24),
    CONFIRM_ACCOUNT("Подтверждение аккаунта", 60 * 60 * 24);
    final String titleEvent;
    final int lifetimeSeconds;

    public String getTitleEvent() {
        return titleEvent;
    }

    public int getLifetimeSeconds() {
        return lifetimeSeconds;
    }


}
