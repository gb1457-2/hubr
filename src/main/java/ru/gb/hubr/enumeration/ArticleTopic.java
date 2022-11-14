package ru.gb.hubr.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleTopic {
    DESIGN("Дизайн"),
    WEB_DEVELOPMENT("Веб-разработка"),
    MOBILE_DEVELOPMENT("Мобильная разработка"),
    MARKETING("Маркетинг");

    private final String title;
}
