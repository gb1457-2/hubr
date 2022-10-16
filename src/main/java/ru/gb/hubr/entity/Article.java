package ru.gb.hubr.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccountUser author;

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