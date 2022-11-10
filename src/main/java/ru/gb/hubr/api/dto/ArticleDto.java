package ru.gb.hubr.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gb.hubr.entity.enums.ArticleTopic;

import javax.validation.constraints.NotBlank;

import static java.lang.Math.min;
import static liquibase.repackaged.org.apache.commons.lang3.StringUtils.ordinalIndexOf;

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
        String endOfParagraph = "</p>";
        int maxPreviewParagraphCount = 2;
        int thirdParagraphEndIndex = ordinalIndexOf(content, endOfParagraph, maxPreviewParagraphCount);
        int endContentIndex = (thirdParagraphEndIndex < 0) ? min(content.length(), maxPreviewLength)
                : min(thirdParagraphEndIndex, min(content.length(), maxPreviewLength));
        return content.substring(0, endContentIndex).concat("...");
    }

}
