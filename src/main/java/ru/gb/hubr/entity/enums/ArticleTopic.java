package ru.gb.hubr.entity.enums;

public enum ArticleTopic {
    DESIGN("Дизайн"), WEB_DEVELOPMENT("Веб-разработка"), MOBILE_DEVELOPMENT("Мобильная разработка"), MARKETING("Маркетинг");

    private String title;

    ArticleTopic(String topic) {
        this.title = topic;
    }

    public String getTitle() {
        return title;
    }

}
