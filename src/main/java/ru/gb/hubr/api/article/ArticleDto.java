package ru.gb.hubr.api.article;


import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.gb.hubr.entity.AccountUser;
import ru.gb.hubr.entity.enums.ArticleTopic;


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
    private ArticleTopic topic;

    @NotBlank
    private String name;

    @NotBlank
    private String content;


    public String getPreview() {
        int maxPreviewLength = 100;
        int endContentIndex = Math.min(content.length(), maxPreviewLength);
        return content.substring(0, endContentIndex).concat("...");
    }

}
