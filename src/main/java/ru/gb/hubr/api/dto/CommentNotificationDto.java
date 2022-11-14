package ru.gb.hubr.api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gb.hubr.enumeration.CommentComplainType;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentNotificationDto {

    private Long commentId;

    private Long articleId;

    private String username;

    @NotBlank
    private CommentComplainType commentComplainType;
}
