package ru.gb.hubr.api.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.gb.hubr.enumeration.ArticleTopic;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @PastOrPresent
    private LocalDateTime createdAt;

    @NotBlank
    private String content;

    private List<CommentDto> comments;

    public String getPreview() {
        int maxPreviewLength = 100;
        String endOfParagraph = "</p>";
        int maxPreviewParagraphCount = 2;
        int thirdParagraphEndIndex = ordinalIndexOf(content, endOfParagraph, maxPreviewParagraphCount);
        int endContentIndex = (thirdParagraphEndIndex < 0) ? min(content.length(), maxPreviewLength)
                : min(thirdParagraphEndIndex, min(content.length(), maxPreviewLength));
        return content.substring(0, endContentIndex).concat("...");
    }

    public String getFormattedCreatedAt(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createdAt.format(formatter);
    }

}
