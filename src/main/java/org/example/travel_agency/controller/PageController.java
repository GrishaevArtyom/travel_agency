package org.example.travel_agency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для обработки запросов к статическим страницам приложения.
 * Предоставляет маршруты для основных информационных страниц, доступных всем пользователям.
 * Страницы, обрабатываемые данным контроллером, не требуют аутентификации пользователей,
 * что настроено в {@link org.example.travel_agency.config.SecurityConfig}.
 */
@Controller
public class PageController {

    /**
     * Обрабатывает GET-запрос к корневому URL приложения.
     * Отображает главную страницу сайта.
     *
     * @return имя шаблона Thymeleaf "home" для отображения главной страницы
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }

    /**
     * Обрабатывает GET-запрос к странице "О нас".
     * Отображает информационную страницу о компании или сервисе.
     *
     * @return имя шаблона Thymeleaf "about" для отображения страницы "О нас"
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
