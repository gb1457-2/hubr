package ru.gb.hubr.api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gb.hubr.enumeration.ArticleComplainType;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleNotificationDto {

    private Long articleId;

    private String username;

    @NotBlank
    private ArticleComplainType articleComplainType;
}
