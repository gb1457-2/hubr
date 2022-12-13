package ru.gb.hubr.controller.admin;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.hubr.api.dto.CommentDto;
import ru.gb.hubr.api.dto.CommentNotificationDto;
import ru.gb.hubr.service.CommentNotificationService;
import ru.gb.hubr.service.CommentService;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/comment-notifications")
public class CommentNotificationAdminController {

    private final CommentNotificationService commentNotificationService;

    private final CommentService commentService;

    @GetMapping("/all")
    public String getCommentNotificationList(Model model) {
        model.addAttribute("commentNotifications", commentNotificationService.findAll());
        return "admin/admin-comment-notifications";
    }

    @GetMapping("/comments/{commentId}")
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

    @GetMapping("/mark-read")
    public String markAsRead(@RequestParam(name = "id") Long id) {
        CommentNotificationDto commentNotificationDto;
        if(id != null) {
            commentNotificationDto = commentNotificationService.findCommentNotificationById(id);
            if(!commentNotificationDto.isRead()) {
                commentNotificationDto.setRead(true);
                commentNotificationService.save(commentNotificationDto);
            }
        }
        return "redirect:/admin/comment-notifications/all";
    }

    @GetMapping("/delete")
    public String deleteOrRestoreCommentNotification(@RequestParam(name = "id") Long id) {
        CommentNotificationDto commentNotificationDto;
        if(id != null) {
            commentNotificationDto = commentNotificationService.findCommentNotificationById(id);
            if(commentNotificationDto.getDeletedAt() != null) {
                commentNotificationDto.setDeletedAt(null);
            } else {
                commentNotificationDto.setRead(true);
                commentNotificationDto.setDeletedAt(LocalDateTime.now());
            }
            commentNotificationService.save(commentNotificationDto);
        }
        return "redirect:/admin/comment-notifications/all";
    }


}

