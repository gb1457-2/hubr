package ru.gb.hubr.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentComplainType {
    PROMOTION("Пожаловаться на рекламу"),
    INCORRECT("Пожаловаться на некорректную информацию"),
    LAW_VIOLATION("Пожаловаться на нарушение закона");

    private final String title;
}
