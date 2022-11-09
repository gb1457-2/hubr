package ru.gb.hubr.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.hubr.api.article.ArticleDto;
import ru.gb.hubr.api.user.UserDto;
import ru.gb.hubr.api.user.profile.ProfileService;
import ru.gb.hubr.entity.enums.ArticleTopic;
import ru.gb.hubr.service.article.ArticleService;

import java.sql.SQLOutput;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/articles")
public class ArticleAdminController {

    private final ArticleService articleService;

    @GetMapping("/all")
    public String getArticleList(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        return "admin/admin-articles";
    }

    @GetMapping("/{articleId}")
    public String readArticle(Model model, @PathVariable("articleId") Long id) {
        ArticleDto articleDto;
        if (id != null) {
            articleDto = articleService.getArticleById(id);
        } else {
            return "redirect:/admin/articles/all";
        }
        model.addAttribute("article", articleDto);
        return "articles/show-article";
    }

    @GetMapping("/edit")
    public String editArticle(Model model, @RequestParam(name = "id") Long id) {
        ArticleDto articleDto;
        if (id != null) {
            articleDto = articleService.getArticleById(id);
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
