package org.example.travel_agency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для управления административной панелью приложения.
 * Предоставляет маршруты и обработчики запросов для административного интерфейса.
 * Доступ к этим маршрутам ограничен пользователями с ролью ADMIN, что настроено в {@link org.example.travel_agency.config.SecurityConfig}.
 */
@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    /**
     * Обрабатывает GET-запрос к корневому URL административной панели.
     * Отображает главную страницу административного интерфейса.
     *
     * @return имя шаблона Thymeleaf для отображения административной панели
     */
    @GetMapping
    public String adminPanel() {
        return "admin";
    }
}
