package ru.gb.hubr.entity;

import lombok.*;
import ru.gb.hubr.entity.common.InfoEntity;
import ru.gb.hubr.entity.enums.ArticleTopic;
import ru.gb.hubr.entity.user.AccountUser;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "article")
public class Article extends InfoEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountUser author;

    @Enumerated(EnumType.STRING)
    @Column(name = "topic")
    private ArticleTopic topic;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    public String getPreview(){

        String[] strings = content.split("</p>");

        String preview = strings[0] + strings[1];

        return preview;
    }

}