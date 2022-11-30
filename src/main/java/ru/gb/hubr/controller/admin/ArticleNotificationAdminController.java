package ru.gb.hubr.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.hubr.service.ArticleNotificationService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/article-notifications")
public class ArticleNotificationAdminController {

    private final ArticleNotificationService articleNotificationService;

    @GetMapping("/all")
    public String getArticleNotificationList(Model model) {
        model.addAttribute("articleNotifications", articleNotificationService.findAll());
        return "admin/admin-article-notifications";
    }

}
