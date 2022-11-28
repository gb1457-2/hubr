package ru.gb.hubr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для перенаправления с корневой страницы сервера приложений до
 * страницы со списком статей
 */
@Controller
@RequestMapping("/")
public class HomeController {

    /**
     * Перенаправляет REST-запрос с корневого URL сервера приложений
     * на страницу со списком статей
     *
     * @return строку с командой перенаправления на страницу со списком статей
     */
    @GetMapping
    public String home() {
        return "redirect:/articles/all";
    }
}
