package ru.gb.hubr.controller.main;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.hubr.api.dto.ArticleDto;
import ru.gb.hubr.api.dto.ArticleNotificationDto;
import ru.gb.hubr.api.dto.CommentDto;
import ru.gb.hubr.api.dto.CommentNotificationDto;
import ru.gb.hubr.api.mapper.ArticleMapper;
import ru.gb.hubr.enumeration.ArticleComplainType;
import ru.gb.hubr.enumeration.ArticleTopic;
import ru.gb.hubr.enumeration.CommentComplainType;
import ru.gb.hubr.service.ArticleService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService service;

    @GetMapping("/all")
    public String getArticlesList(Model model, @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size,
                                  @RequestParam(value = "topic", required = false) Optional<String> topic,
                                  @RequestParam(value = "author", required = false) Optional<String> author){

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        ArticleTopic articleTopicFromFilter;
        if (topic.isPresent()) {
            articleTopicFromFilter = ArticleTopic.getArticleTopicByEnumValue(topic.get());
        } else {
            articleTopicFromFilter = ArticleTopic.NOT_SELECTED;
        }
        model.addAttribute("savedTopic", articleTopicFromFilter);

        String authorFromFilter = author.orElse("");
        model.addAttribute("author", authorFromFilter);

        Page<ArticleDto> articlesPage;

        try {
            if (topic.isPresent() && !topic.get().equals("") && (author.isEmpty() || author.get().equals(""))) {
                articlesPage = service.getArticlesPageByTopic(PageRequest.of(currentPage - 1, pageSize,
                        Sort.by(Sort.Direction.DESC, "createdAt")), topic.get());
                model.addAttribute("topic", topic.get());
            } else if ((topic.isEmpty() || topic.get().equals("")) && author.isPresent() && !author.get().equals("")) {
                articlesPage = service.getArticlesPageByAuthor(PageRequest.of(currentPage - 1, pageSize,
                        Sort.by(Sort.Direction.DESC, "createdAt")), author.get());
            } else if ((topic.isPresent() && !topic.get().equals("")) && (author.isPresent() && !author.get().equals(""))) {
                articlesPage = service.getArticlesPageByTopicAndAuthor(PageRequest.of(currentPage - 1, pageSize,
                        Sort.by(Sort.Direction.DESC, "createdAt")), topic.get(), author.get());
            } else {
                articlesPage = service.getArticlesPage(PageRequest.of(currentPage - 1, pageSize,
                        Sort.by(Sort.Direction.DESC, "createdAt")));
            }
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
            if (totalPages >= 10 && currentPage > 5) {
                firstIndexPagination = currentPage - 4;
                lastIndexPagination = currentPage + 5;
                if (lastIndexPagination >= totalPages) {
                    lastIndexPagination = totalPages;
                    firstIndexPagination = totalPages - 9;
                }
            }
            model.addAttribute("firstIndexPagination", firstIndexPagination);
            model.addAttribute("lastIndexPagination", lastIndexPagination);

        } catch (NoSuchElementException e) {

            articlesPage = Page.empty();
            model.addAttribute("articlesPage", articlesPage);

            List<Integer> pageNumbers = new ArrayList<>();
            int firstIndexPagination = 0;
            int lastIndexPagination = 0;

            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", currentPage);

            model.addAttribute("firstIndexPagination", firstIndexPagination);
            model.addAttribute("lastIndexPagination", lastIndexPagination);

        }

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
