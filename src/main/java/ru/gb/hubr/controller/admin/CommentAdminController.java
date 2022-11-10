package ru.gb.hubr.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class CommentAdminController {


    @GetMapping("/all")
    public String getArticleList(Model model) {

        return "admin/admin-comments";
    }

}
