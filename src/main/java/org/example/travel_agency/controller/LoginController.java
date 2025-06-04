package org.example.travel_agency.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
     * При наличии параметра error добавляет сообщение об ошибке в модель.
     *
     * @param error параметр, указывающий на ошибку аутентификации
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона Thymeleaf "login" для отображения страницы входа
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
        }
        return "login";
    }
}
