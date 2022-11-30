package ru.gb.hubr.controller.admin;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.hubr.service.CommentNotificationService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/comment-notifications")
public class CommentNotificationAdminController {

    private final CommentNotificationService commentNotificationService;

    @GetMapping("/all")
    public String getCommentNotificationList(Model model) {
        model.addAttribute("commentNotifications", commentNotificationService.findAll());
        return "admin/admin-comment-notifications";
    }

}
