package ru.gb.hubr.controller.main;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.hubr.api.dto.ArticleDto;
import ru.gb.hubr.api.dto.ArticleNotificationDto;
import ru.gb.hubr.api.dto.CommentDto;
import ru.gb.hubr.api.dto.CommentNotificationDto;
import ru.gb.hubr.enumeration.ArticleComplainType;
import ru.gb.hubr.enumeration.ArticleTopic;
import ru.gb.hubr.enumeration.CommentComplainType;
import ru.gb.hubr.service.ArticleService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService service;

    @GetMapping("/all")
    public String getArticlesList(Model model) {
        model.addAttribute("articles", service.getAllArticles());
        return "articles/articles";
    }

    @PostMapping("/add")
    public String saveArticle(@AuthenticationPrincipal UserDetails user,
                              ArticleDto articleDto) {
        articleDto.setAuthor(user.getUsername());
        service.saveArticle(articleDto);
        return "redirect:/articles/all";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        ArticleDto articleDto = new ArticleDto();
        model.addAttribute("topics", ArticleTopic.values());
        model.addAttribute("article", articleDto);
        return "articles/add-article";
    }

    @GetMapping("/{id}")
    public String showArticle(Model model, @PathVariable(name = "id") Long id) {

        ArticleDto articleDto = service.getArticleById(id);
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


}
