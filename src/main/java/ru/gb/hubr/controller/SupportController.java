package ru.gb.hubr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для отображения страницы Помощь
 */
@Controller
@RequestMapping("/support")
public class SupportController {

    @GetMapping
    public String getSupportPage() {
        return "/common/support";
    }

}
