package ru.gb.hubr.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArticleComplainType {
    PROMOTION("На рекламу"),
    INCORRECT("На некорректную информацию"),
    LAW_VIOLATION("На нарушение закона");

    private final String title;
}
