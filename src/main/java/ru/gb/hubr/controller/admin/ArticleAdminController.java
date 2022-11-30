package ru.gb.hubr.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.hubr.api.dto.ArticleDto;
import ru.gb.hubr.api.dto.ArticleNotificationDto;
import ru.gb.hubr.api.dto.CommentDto;
import ru.gb.hubr.api.dto.CommentNotificationDto;
import ru.gb.hubr.enumeration.ArticleComplainType;
import ru.gb.hubr.enumeration.ArticleTopic;
import ru.gb.hubr.service.AccountUserService;
import ru.gb.hubr.enumeration.CommentComplainType;
import ru.gb.hubr.service.ArticleService;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/articles")
public class ArticleAdminController {

    private final ArticleService articleService;

    private final AccountUserService accountUserService;

    @GetMapping("/all")
    public String getArticleList(HttpSession session, Model model) {
        model.addAttribute("articles", articleService.getAllArticles(
                accountUserService.getCurrentUsername(session)
        ));
        return "admin/admin-articles";
    }

    @GetMapping("/{articleId}")
    public String readArticle(HttpSession session, Model model, @PathVariable("articleId") Long id) {
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

    @GetMapping("/edit")
    public String editArticle(HttpSession session, Model model, @RequestParam(name = "id") Long id) {
        ArticleDto articleDto;
        if (id != null) {
            articleDto = articleService.getArticleById(id, accountUserService.getCurrentUsername(session));
        } else {
            return "redirect:/admin/articles/all";
        }
        model.addAttribute("topics", ArticleTopic.values());
        model.addAttribute("article", articleDto);
        return "articles/add-article";
    }

    @PutMapping("/ban")
    public String banArticle(@RequestParam(name = "id") Long id) {
        //  UserDto userDto = profileServiceSQL.findById(id);
        return "redirect:admin/articles/all";
    }

    @DeleteMapping("/delete")
    public String deleteArticle(@RequestParam(name = "id") Long id) {
        //   UserDto userDto = profileServiceSQL.findById(id);
        return "redirect:admin/articles/all";
    }

}
