package ru.gb.hubr.api.article;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @PastOrPresent
    private LocalDateTime createdAt;

    public String getPreview(){

        String[] strings = content.split("</p>");

        String preview = strings[0] + strings[1];

        return preview;
    }

    public String getFormattedCreatedAt(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createdAt.format(formatter);
    }

}
