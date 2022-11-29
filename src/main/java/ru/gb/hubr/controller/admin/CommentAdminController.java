package ru.gb.hubr.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.hubr.api.dto.ArticleDto;
import ru.gb.hubr.api.dto.ArticleNotificationDto;
import ru.gb.hubr.api.dto.CommentDto;
import ru.gb.hubr.api.dto.CommentNotificationDto;
import ru.gb.hubr.enumeration.ArticleComplainType;
import ru.gb.hubr.enumeration.ArticleTopic;
import ru.gb.hubr.enumeration.CommentComplainType;
import ru.gb.hubr.service.CommentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class CommentAdminController {

    private final CommentService commentService;

    @GetMapping("/all")
    public String getCommentList(Model model) {
        model.addAttribute("comments", commentService.findAll());
        return "admin/admin-comments";
    }

    @GetMapping("/{commentId}")
    public String showComment(Model model, @PathVariable("commentId") Long id) {
        CommentDto commentDto;
        if (id != null) {
            commentDto = commentService.findCommentById(id);
        } else {
            return "redirect:/admin/comments/all";
        }
        model.addAttribute("comment", commentDto);
        return "admin/show-comment";
    }

    @GetMapping("/edit")
    public String showCommentEditForm(Model model, @RequestParam(name = "id") Long id) {
        CommentDto commentDto;
        if (id != null) {
            commentDto = commentService.findCommentById(id);
        } else {
            return "redirect:/admin/comments/all";
        }
        model.addAttribute("comment", commentDto);
        return "admin/edit-comment";
    }


    @PostMapping("/edit")
    public String saveComment(CommentDto commentDto) {
        if(commentDto.getContent() != null || !commentDto.getContent().equals("")) {
            commentService.save(commentDto);
            return "redirect:/admin/comments/all";
        }
        return "admin/edit-comment";
    }

}

