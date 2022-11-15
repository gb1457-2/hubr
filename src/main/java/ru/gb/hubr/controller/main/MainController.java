package ru.gb.hubr.controller.main;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.hubr.service.article.ArticleService;
import ru.gb.hubr.api.article.ArticleDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class MainController {

    private final ArticleService service;

    @PostMapping("/add")
    public String saveProduct(ArticleDto articleDto) {
        service.saveArticle(articleDto);
        return "redirect:/articles/all";
    }

    @GetMapping("/add")
    public String showForm(Model model) {
        ArticleDto articleDto = new ArticleDto();
        model.addAttribute("article", articleDto);
        return "articles/add-article";
    }

    @GetMapping("/{id}")
    public String showArticle(Model model, @PathVariable(name = "id") Long id){

        ArticleDto articleDto = service.getArticleById(id);
        model.addAttribute("article", articleDto);
        return "articles/show-article";
    }

    @GetMapping("/all")
    public String getArticlesList(Model model, @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<ArticleDto> articlesPage = service.getArticlesPage(PageRequest.of(currentPage-1, pageSize,
                Sort.by(Sort.Direction.DESC, "createdAt")));
        model.addAttribute("articlesPage", articlesPage);
        int totalPages = articlesPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("currentPage", currentPage);
        int firstIndexPagination = 1;
        int lastIndexPagination = 10;
        if (totalPages >= 10 && currentPage > 5){
           firstIndexPagination = currentPage - 4;
           lastIndexPagination = currentPage + 5;
           if (lastIndexPagination >= totalPages){
               lastIndexPagination = totalPages;
               firstIndexPagination = totalPages - 9;
           }
        }
        model.addAttribute("firstIndexPagination", firstIndexPagination);
        model.addAttribute("lastIndexPagination", lastIndexPagination);
        return "articles/main";
    }


}
