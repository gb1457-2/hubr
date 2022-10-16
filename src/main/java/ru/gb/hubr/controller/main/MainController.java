package ru.gb.hubr.controller.main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.hubr.entity.AccountUser;
import ru.gb.hubr.entity.Article;
import ru.gb.hubr.service.ArticleService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class MainController {

    private final ArticleService service;

    @GetMapping("/all")
    public String getArticlesList(Model model){
        model.addAttribute("articles", service.getAllArticles());
        return "articles/main";
    }

    @PostMapping("/add")
    public String saveProduct(Article article) {
        service.saveArticle(article);
        return "redirect:/articles/all";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        Article article = new Article();
        model.addAttribute("article", article);
        return "articles/add-article";
    }

    @GetMapping("/{id}")
    public String showArticle(Model model, @PathVariable(name = "id") Long id){

        Article article = service.getArticleById(id);
        model.addAttribute("article", article);
        return "articles/show-article";
    }


}
