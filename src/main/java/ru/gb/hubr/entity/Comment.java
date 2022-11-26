package ru.gb.hubr.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gb.hubr.entity.common.InfoEntity;
import ru.gb.hubr.entity.user.AccountUser;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends InfoEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountUser author;


    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.MERGE)
    private List<CommentNotification> complains;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.MERGE)
    private List<CommentLike> likes;
}