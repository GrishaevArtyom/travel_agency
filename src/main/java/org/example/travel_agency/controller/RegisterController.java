package org.example.travel_agency.controller;

import lombok.RequiredArgsConstructor;
import org.example.travel_agency.model.User;
import org.example.travel_agency.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Контроллер для управления процессом регистрации новых пользователей.
 * Предоставляет маршруты для отображения формы регистрации и обработки
 * отправленных данных регистрации.
 * Выполняет хеширование паролей и установку роли пользователя по умолчанию.
 */
@Controller
@RequiredArgsConstructor
public class RegisterController {

    /**
     * Сервис для работы с пользовательскими данными.
     */
    private final UserService userService;

    /**
     * Кодировщик паролей для безопасного хранения паролей в базе данных.
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Обрабатывает GET-запрос на страницу регистрации.
     * Добавляет пустой объект пользователя в модель для привязки данных формы.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона Thymeleaf "register" для отображения формы регистрации
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Обрабатывает POST-запрос на регистрацию пользователя.
     * Кодирует пароль пользователя, устанавливает роль "USER" и сохраняет данные в базе.
     *
     * @param user объект пользователя с данными из формы регистрации
     * @return перенаправление на страницу входа после успешной регистрации
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userService.save(user);
        return "redirect:/login";
    }
}
