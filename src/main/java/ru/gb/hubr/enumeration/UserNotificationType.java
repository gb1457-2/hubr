package ru.gb.hubr.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserNotificationType {
    BAN("Вы забанены до "), ADMIN_APPOINTMENT("Вы назначены администратором"), ADMIN_DISMISS("Вы лишены прав администратора");

    private final String message;

}
