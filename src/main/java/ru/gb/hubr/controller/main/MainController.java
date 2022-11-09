package ru.gb.hubr.controller.main;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.hubr.api.user.UserDto;
import ru.gb.hubr.api.user.profile.ProfileService;
import ru.gb.hubr.entity.AccountUser;
import ru.gb.hubr.entity.enums.ArticleTopic;
import ru.gb.hubr.service.article.ArticleService;
import ru.gb.hubr.api.article.ArticleDto;
import ru.gb.hubr.service.mapper.UserMapper;
import ru.gb.hubr.service.profile.ProfileServiceSQL;

import javax.servlet.http.HttpSession;


@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class MainController {

    private final ArticleService service;
    private final ProfileServiceSQL profileServiceSQL;
    private final UserMapper userMapper;

    @GetMapping("/all")
    public String getArticlesList(Model model){
        model.addAttribute("articles", service.getAllArticles());
        return "articles/main";
    }

    @PostMapping("/add")
    public String saveArticle(ArticleDto articleDto) {

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
    public String showArticle(Model model, @PathVariable(name = "id") Long id){

        ArticleDto articleDto = service.getArticleById(id);
        model.addAttribute("article", articleDto);
        return "articles/show-article";
    }


}
