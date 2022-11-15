package ru.gb.hubr.entity;

import lombok.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.gb.hubr.entity.common.InfoEntity;
import javax.persistence.*;
import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "article")
@EntityListeners(AuditingEntityListener.class)
public class Article extends InfoEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountUser author;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Builder
    public Article(Long id, int version, LocalDateTime createdAt, LocalDateTime deletedAt,
                   LocalDateTime lastModifiedAt, AccountUser author, String name, String content) {
        super(id, version, createdAt, deletedAt, lastModifiedAt);
        this.author = author;
        this.name = name;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "author=" + author +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}