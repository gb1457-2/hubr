package ru.gb.hubr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.hubr.api.dto.ArticleDto;
import ru.gb.hubr.api.dto.ArticleNotificationDto;
import ru.gb.hubr.api.dto.CommentDto;
import ru.gb.hubr.api.dto.CommentNotificationDto;
import ru.gb.hubr.enumeration.ArticleComplainType;
import ru.gb.hubr.enumeration.ArticleTopic;
import ru.gb.hubr.enumeration.CommentComplainType;
import ru.gb.hubr.service.AccountUserService;
import ru.gb.hubr.service.ArticleService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Контроллер для работы со статьями
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    private final AccountUserService accountUserService;

    /**
     * Возвращает список статей с учетом параметров пагинации
     *
     * @param model объект для хранения атрибутов
     * @param page  номер страницы
     * @param size  размер страницы
     * @return путь до страницы со статьями
     */
    @GetMapping("/all")
    public String getArticlesList(Model model, @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<ArticleDto> articlesPage = articleService.getArticlesPage(PageRequest.of(currentPage - 1, pageSize,
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

        return "articles/articles";
    }

    /**
     * Сохраняет статью
     *
     * @param user       пользователь
     * @param articleDto DTO со статьёй
     * @return строку редиректа на эндпоинт со списком статей
     */
    @PostMapping("/add")
    public String saveArticle(@AuthenticationPrincipal UserDetails user,
                              ArticleDto articleDto, @RequestParam(required = false, name = "isPublished") String isPublished) {
        articleDto.setAuthor(user.getUsername());
        articleDto.setPublished("on".equals(isPublished));
        articleService.saveArticle(articleDto, user.getUsername());
        return "redirect:/articles/all";
    }

    /**
     * Передаёт данные для отображения формы со статьёй
     *
     * @param model объект для хранения атрибутов
     * @return путь до страницы формаой добавления новой статьи
     */
    @GetMapping("/add")
    public String showForm(HttpSession session, Model model, @RequestParam(required = false) Long id) {

        ArticleDto articleDto = id == null ? new ArticleDto() :
                articleService.getArticleById(id, accountUserService.getCurrentUsername(session));
        if(articleDto.getDeletedAt() != null) {
            return "redirect:/articles/all";
        }

        model.addAttribute("topics", ArticleTopic.values());
        model.addAttribute("article", articleDto);

        return "articles/add-article";
    }


    /**
     * Получает данные о статье с заданным идентификатором и
     * передаёт их для отображения на страницу статьи
     *
     * @param session сессия
     * @param model   объект с атрибутами
     * @param id      идентификатор статьи
     * @return путь до страницы с отображением статьи
     */
    @GetMapping("/{id}")
    public String showArticle(HttpSession session,
                              Model model,
                              @PathVariable Long id) {
        String currentUserName = accountUserService.getCurrentUsername(session);
        ArticleDto articleDto = articleService.getArticleById(id, currentUserName);

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
