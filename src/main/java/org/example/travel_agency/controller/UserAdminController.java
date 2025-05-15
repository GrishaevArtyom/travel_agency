package org.example.travel_agency.controller;

import org.example.travel_agency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для административного управления пользователями.
 * Предоставляет функциональность для просмотра списка всех пользователей,
 * удаления учётных записей и изменения ролей пользователей.
 * Доступ к этим маршрутам требует наличия прав администратора,
 * что настроено в {@link org.example.travel_agency.config.SecurityConfig}.
 */
@Controller
@RequestMapping("/admin/users")
public class UserAdminController {

    /**
     * Сервис для работы с данными пользователей.
     */
    private final UserService userService;

    /**
     * Создает новый экземпляр контроллера администрирования пользователей с необходимым сервисом.
     *
     * @param userService сервис для управления пользователями
     */
    @Autowired
    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Обрабатывает GET-запрос к странице управления пользователями.
     * Загружает список всех пользователей системы для отображения.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона Thymeleaf "admin_users" для отображения страницы управления пользователями
     */
    @GetMapping
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin_users";
    }

    /**
     * Обрабатывает POST-запрос на удаление пользователя.
     * Удаляет пользователя из системы по указанному идентификатору.
     *
     * @param id идентификатор пользователя для удаления
     * @return перенаправление на страницу управления пользователями после удаления
     */
    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    /**
     * Обрабатывает POST-запрос на изменение роли пользователя.
     * Обновляет роль пользователя в системе на указанную.
     *
     * @param id идентификатор пользователя для обновления роли
     * @param role новая роль пользователя
     * @return перенаправление на страницу управления пользователями после обновления
     */
    @PostMapping("/update-role/{id}")
    public String updateUserRole(@PathVariable Long id, @RequestParam String role) {
        userService.updateUserRole(id, role);
        return "redirect:/admin/users";
    }
}
