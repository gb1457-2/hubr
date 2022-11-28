package ru.gb.hubr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контролле для отображения страницы с ошибкой доступа
 */
@Controller
@RequestMapping("/errors")
public class ErrorController {

    /**
     * Возвращает путь до страницы с ошибкой доступа
     *
     * @return строка с путём до страницы с ошибкой доступа
     */
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
