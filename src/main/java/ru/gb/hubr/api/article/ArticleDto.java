package ru.gb.hubr.api.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public String getPreview() {
        int maxPreviewLength = 100;
        int endContentIndex = Math.min(content.length(), maxPreviewLength);
        return content.substring(0, endContentIndex).concat("...");
    }

}
