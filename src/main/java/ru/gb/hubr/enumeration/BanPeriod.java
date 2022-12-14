package ru.gb.hubr.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BanPeriod {

    ONE_WEEK("1 неделя"), TWO_WEEKS("2 недели"), MONTH("1 месяц"), FOREVER("Навсегда");

    private final String duration;
}
