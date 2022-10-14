package ru.gb.hubr.entity;

import lombok.*;
import ru.gb.hubr.entity.common.InfoEntity;

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

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

}