package ru.gb.hubr.api.article;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.gb.hubr.entity.AccountUser;

import javax.persistence.EntityListeners;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDto {

    private Long id;

    @NotBlank
    private String author;

    @NotBlank
    private String name;

    @NotBlank
    private String content;

    public String getPreview(){

        String[] strings = content.split("</p>");

        String preview = strings[0] + strings[1];

        return preview;
    }

}
