package ru.gb.hubr.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.hubr.api.dto.ArticleDto;
import ru.gb.hubr.api.dto.ArticleNotificationDto;
import ru.gb.hubr.api.dto.CommentDto;
import ru.gb.hubr.api.dto.CommentNotificationDto;
import ru.gb.hubr.enumeration.ArticleComplainType;
import ru.gb.hubr.enumeration.CommentComplainType;
import ru.gb.hubr.service.AccountUserService;
import ru.gb.hubr.service.ArticleNotificationService;
import ru.gb.hubr.service.ArticleService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/article-notifications")
public class ArticleNotificationAdminController {

    private final ArticleNotificationService articleNotificationService;

    private final AccountUserService accountUserService;

    private final ArticleService articleService;

    @GetMapping("/all")
    public String getArticleNotificationList(Model model) {
        model.addAttribute("articleNotifications", articleNotificationService.findAll());
        return "admin/admin-article-notifications";
    }

    @GetMapping("/articles/{articleId}")
    public String showArticle(HttpSession session, Model model, @PathVariable("articleId") Long id) {
        ArticleDto articleDto;
        if (id != null) {
            articleDto = articleService.getArticleById(id, accountUserService.getCurrentUsername(session));
        } else {
            return "redirect:/admin/articles/all";
        }
        model.addAttribute("article", articleDto);
        CommentDto comment = new CommentDto();
        comment.setArticleId(id);
        model.addAttribute("comment", comment);
        model.addAttribute("articleComplainTypes", ArticleComplainType.values());
        model.addAttribute("articleNotification", new ArticleNotificationDto());
        model.addAttribute("commentComplainTypes", CommentComplainType.values());
        model.addAttribute("commentNotification", new CommentNotificationDto());
        return "articles/show-article";
    }

    @GetMapping("/mark-read")
    public String markAsRead(@RequestParam(name = "id") Long id) {
        ArticleNotificationDto articleNotificationDto;
        if(id != null) {
            articleNotificationDto = articleNotificationService.findArticleNotificationById(id);
            if(!articleNotificationDto.isRead()) {
                articleNotificationDto.setRead(true);
                articleNotificationService.save(articleNotificationDto);
            }
        }
        return "redirect:/admin/article-notifications/all";
    }

    @GetMapping("/delete")
    public String deleteOrRestoreArticleNotification(@RequestParam(name = "id") Long id) {
        ArticleNotificationDto articleNotificationDto;
        if (id != null) {
            articleNotificationDto = articleNotificationService.findArticleNotificationById(id);
            if(articleNotificationDto.getDeletedAt() != null) {
                articleNotificationDto.setDeletedAt(null);
            } else {
                articleNotificationDto.setRead(true);
                articleNotificationDto.setDeletedAt(LocalDateTime.now());
            }
            articleNotificationService.save(articleNotificationDto);
        }
        return "redirect:/admin/article-notifications/all";
    }

}

