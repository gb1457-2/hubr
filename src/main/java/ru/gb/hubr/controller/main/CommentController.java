package ru.gb.hubr.controller.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.hubr.api.dto.CommentDto;
import ru.gb.hubr.service.CommentService;

import static java.time.LocalDateTime.now;


@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
@Slf4j
public class CommentController {

    private final CommentService service;

    @PostMapping
    public String saveComment(@AuthenticationPrincipal UserDetails user,
                              CommentDto commentDto) {
        log.info("Сохраняется комментарий пользователя {} по статье с идентификтором {}",
                user.getUsername(), commentDto.getArticleId());
        commentDto.setUsername(user.getUsername());
        commentDto.setCreatedAt(now());
        service.save(commentDto, user.getUsername());
        return "redirect:/articles/" + commentDto.getArticleId();
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Long commentId, Model model) {
        log.info("Удаляется комментарий с идентификтором {}", commentId);
        Long articleId = (Long) model.getAttribute("articleId");
        service.delete(commentId);
        return "redirect:/articles/id" + articleId;
    }
}
