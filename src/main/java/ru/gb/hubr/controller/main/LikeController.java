package ru.gb.hubr.controller.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.hubr.service.ArticleLikeService;
import ru.gb.hubr.service.CommentLikeService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
@Slf4j
public class LikeController {

    private final ArticleLikeService articleLikeService;

    private final CommentLikeService commentLikeService;

    @GetMapping("/{articleId}/likes/add")
    public String addNewArticleLike(@AuthenticationPrincipal UserDetails user,
                                    @PathVariable long articleId) {
        log.info("Добавляется лайк пользователя {} по статье с идентификтором {}",
                user.getUsername(), articleId);
        articleLikeService.add(articleId, user.getUsername());
        return "redirect:/articles/" + articleId;
    }

    @GetMapping("/{articleId}/likes/{articleLikeId}/delete")
    public String logicalDeleteArticleLike(@AuthenticationPrincipal UserDetails user,
                                           @PathVariable Long articleId,
                                           @PathVariable Long articleLikeId) {
        log.info("Проводится логическое удаление лайка {} пользователя {} на статью с идентификтором {}",
                articleLikeId, user.getUsername(), articleId);
        articleLikeService.logicalDelete(articleLikeId, user.getUsername());
        return "redirect:/articles/" + articleId;
    }

    @GetMapping("/{articleId}/comments/{commentId}/likes/add")
    public String addNewCommentLike(@AuthenticationPrincipal UserDetails user,
                                    @PathVariable Long articleId,
                                    @PathVariable long commentId) {
        log.info("Добавляется лайк пользователя {} по комментарию с идентификтором {}",
                user.getUsername(), commentId);
        commentLikeService.add(commentId, user.getUsername());
        return "redirect:/articles/" + articleId;
    }

    @GetMapping("/{articleId}/comments/{commentId}/likes/{commentLikeId}/delete")
    public String logicalDeleteCommentLike(@AuthenticationPrincipal UserDetails user,
                                           @PathVariable Long articleId,
                                           @PathVariable Long commentId,
                                           @PathVariable Long commentLikeId) {
        log.info("Проводится логическое удаление лайка {} пользователя {} на комментарий с идентификтором {}",
                commentLikeId, user.getUsername(), commentId);
        commentLikeService.logicalDelete(commentLikeId, user.getUsername());
        return "redirect:/articles/" + articleId;
    }
}
