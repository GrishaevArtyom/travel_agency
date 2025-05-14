package org.example.travel_agency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для обработки запросов, связанных с аутентификацией пользователей.
 * Предоставляет маршрут для отображения страницы входа в систему.
 * Обработка формы входа выполняется автоматически Spring Security,
 * что настроено в {@link org.example.travel_agency.config.SecurityConfig}.
 */
@Controller
public class LoginController {

    /**
     * Обрабатывает GET-запрос на страницу входа.
     * Отображает форму аутентификации пользователя.
     *
     * @return имя шаблона Thymeleaf "login" для отображения страницы входа
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
