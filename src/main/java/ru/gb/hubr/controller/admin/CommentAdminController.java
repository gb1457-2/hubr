package ru.gb.hubr.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.hubr.api.dto.CommentDto;
import ru.gb.hubr.service.AccountUserService;
import ru.gb.hubr.service.CommentService;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class CommentAdminController {

    private final CommentService commentService;
    private final AccountUserService accountUserService;

    @GetMapping("/all")
    public String getCommentList(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("comments", commentService.findAll(user.getUsername()));
        return "admin/admin-comments";
    }

    @GetMapping("/{commentId}")
    public String showComment(@AuthenticationPrincipal UserDetails user, Model model,
                              @PathVariable("commentId") Long id) {
        CommentDto commentDto;
        if (id != null) {
            commentDto = commentService.findCommentById(id, user.getUsername());
        } else {
            return "redirect:/admin/comments/all";
        }
        model.addAttribute("comment", commentDto);
        return "admin/show-comment";
    }

    @GetMapping("/edit")
    public String showCommentEditForm(@AuthenticationPrincipal UserDetails user,
                                      Model model, @RequestParam(name = "id") Long id) {
        CommentDto commentDto;
        if (id != null) {
            commentDto = commentService.findCommentById(id, user.getUsername());
            if(commentDto.getDeletedAt() != null) {
                return "redirect:/admin/comments/all";
            }
        } else {
            return "redirect:/admin/comments/all";
        }
        model.addAttribute("comment", commentDto);
        return "admin/edit-comment";
    }


    @PostMapping("/edit")
    public String saveComment(@AuthenticationPrincipal UserDetails user,
                              CommentDto commentDto) {
        if (commentDto.getContent() != null || !commentDto.getContent().equals("")) {
            commentService.save(commentDto, user.getUsername());
            return "redirect:/admin/comments/all";
        }
        return "admin/edit-comment";
    }

    @GetMapping("/delete")
    public String deleteOrRestoreComment(@AuthenticationPrincipal UserDetails user, @RequestParam(name = "id") Long id) {
        CommentDto commentDto;
        if(id != null) {
            commentDto = commentService.findCommentById(id, user.getUsername());
            if(commentDto.getDeletedAt() != null) {
                commentDto.setDeletedAt(null);
            } else {
                commentDto.setDeletedAt(LocalDateTime.now());
            }
            commentService.save(commentDto, user.getUsername());
        }
        return "redirect:/admin/comments/all";
    }

}

