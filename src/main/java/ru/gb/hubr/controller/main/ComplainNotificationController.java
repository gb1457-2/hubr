package ru.gb.hubr.controller.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import ru.gb.hubr.api.dto.ArticleNotificationDto;
import ru.gb.hubr.api.dto.CommentNotificationDto;
import ru.gb.hubr.service.ArticleNotificationService;
import ru.gb.hubr.service.CommentNotificationService;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ComplainNotificationController {

    private final CommentNotificationService commentNotificationService;
    private final ArticleNotificationService articleNotificationService;

    @PostMapping("/comments/complain")
    public String saveCommentComplain(@AuthenticationPrincipal UserDetails user,
                                      CommentNotificationDto notification) {
        Long articleId = notification.getArticleId();
        Long commentId = notification.getCommentId();
        log.info("Сохраняется жалоба на комментарий {} пользователя {} по статье с идентификтором {}",
                commentId, user.getUsername(), articleId);
        notification.setUsername(user.getUsername());
        commentNotificationService.save(notification);
        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/articles/complain")
    public String saveArticleComplain(@AuthenticationPrincipal UserDetails user,
                                      ArticleNotificationDto notification) {
        Long articleId = notification.getArticleId();
        log.info("Сохраняется жалоба пользователя {} на статью с идентификтором {}",
                user.getUsername(), articleId);
        System.out.println("User Details user: " + user.getUsername());
        notification.setUsername(user.getUsername());
        System.out.println("User from notification: " + notification.getUsername());
        articleNotificationService.save(notification);
        return "redirect:/articles/" + articleId;
    }
}
