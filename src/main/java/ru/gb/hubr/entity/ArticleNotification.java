package ru.gb.hubr.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gb.hubr.entity.common.CreateDeleteEventEntity;
import ru.gb.hubr.entity.user.AccountUser;
import ru.gb.hubr.enumeration.ArticleComplainType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "article_notification")
public class ArticleNotification extends CreateDeleteEventEntity {

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Enumerated(EnumType.STRING)
    @Column(name = "complain_type")
    private ArticleComplainType complainType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountUser author;

    @Column(name = "is_read")
    private boolean isRead;

}
