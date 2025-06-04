package org.example.travel_agency.controller;

import org.example.travel_agency.model.Booking;
import org.example.travel_agency.model.User;
import org.example.travel_agency.service.BookingService;
import org.example.travel_agency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

/**
 * Контроллер для управления профилем пользователя.
 * Предоставляет функциональность для просмотра и управления личной информацией пользователя,
 * включая просмотр забронированных туров и возможность их отмены.
 * Доступ к этим маршрутам требует аутентификации пользователя,
 * что настроено в {@link org.example.travel_agency.config.SecurityConfig}.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    /**
     * Сервис для работы с бронированиями пользователя.
     */
    private final BookingService bookingService;

    /**
     * Сервис для работы с пользовательскими данными.
     */
    private final UserService userService;

    /**
     * Создает новый экземпляр контроллера профиля с необходимыми сервисами.
     *
     * @param bookingService сервис для управления бронированиями пользователя
     * @param userService сервис для получения информации о пользователе
     */
    @Autowired
    public ProfileController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    /**
     * Обрабатывает GET-запрос на страницу профиля пользователя.
     * Загружает информацию о текущем пользователе и его бронированиях.
     *
     * @param model объект модели для передачи данных в представление
     * @param principal объект, содержащий информацию о текущем аутентифицированном пользователе
     * @return имя шаблона Thymeleaf "profile" для отображения страницы профиля
     */
    @GetMapping
    public String userProfile(Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        List<Booking> bookings = bookingService.getBookingsByUsername(user.getUsername());
        model.addAttribute("bookings", bookings);
        return "profile";
    }

    /**
     * Обрабатывает POST-запрос на отмену бронирования.
     * Удаляет бронирование только если оно принадлежит текущему пользователю.
     *
     * @param id идентификатор бронирования для отмены
     * @param principal объект, содержащий информацию о текущем аутентифицированном пользователе
     * @return перенаправление на страницу профиля пользователя после обработки запроса
     */
    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, Principal principal) {
        Booking booking = bookingService.findById(id);

        if (booking != null && booking.getUsername().equals(principal.getName())) {
            bookingService.deleteBooking(id);
        }

        return "redirect:/profile";
    }
}
