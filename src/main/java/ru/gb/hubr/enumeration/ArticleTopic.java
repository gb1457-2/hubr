package ru.gb.hubr.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public enum ArticleTopic {
    DESIGN("Дизайн"),
    WEB_DEVELOPMENT("Веб-разработка"),
    MOBILE_DEVELOPMENT("Мобильная разработка"),
    MARKETING("Маркетинг"),
    NOT_SELECTED("");

    private final String title;

    public static ArticleTopic getArticleTopicByEnumValue(String enumValue){
        switch (enumValue){
            case "web_development": return ArticleTopic.WEB_DEVELOPMENT;
            case "design": return ArticleTopic.DESIGN;
            case "mobile_development": return ArticleTopic.MOBILE_DEVELOPMENT;
            case "marketing": return ArticleTopic.MARKETING;
            default: return ArticleTopic.NOT_SELECTED;
        }
    }
}
