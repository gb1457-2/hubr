package ru.gb.hubr.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gb.hubr.entity.common.InfoEntity;
import ru.gb.hubr.enumeration.ArticleTopic;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
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

    @OneToMany(mappedBy = "article", cascade = CascadeType.MERGE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "article", cascade = CascadeType.MERGE)
    private List<ArticleNotification> complains;

    public String getPreview() {

        String[] strings = content.split("</p>");

        String preview = strings[0] + strings[1];

        return preview;
    }

}