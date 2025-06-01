package org.example.travel_agency.controller;

import org.example.travel_agency.model.Booking;
import org.example.travel_agency.model.Tour;
import org.example.travel_agency.model.User;
import org.example.travel_agency.service.BookingService;
import org.example.travel_agency.service.TourService;
import org.example.travel_agency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;

/**
 * Контроллер для управления процессом бронирования туров.
 * Обрабатывает запросы, связанные с созданием и обработкой бронирований пользователей.
 * Работает с сервисами бронирования, туров и пользователей для координации операций бронирования.
 */
@Controller
@RequestMapping("/booking")
public class BookingController {

    /**
     * Сервис для работы с бронированиями.
     */
    private final BookingService bookingService;

    /**
     * Сервис для работы с турами.
     */
    private final TourService tourService;

    /**
     * Сервис для работы с пользователями.
     */
    private final UserService userService;

    /**
     * Создает новый экземпляр контроллера бронирования с необходимыми сервисами.
     *
     * @param bookingService сервис для управления бронированиями
     * @param tourService    сервис для получения информации о турах
     * @param userService    сервис для получения информации о пользователях
     */
    @Autowired
    public BookingController(BookingService bookingService, TourService tourService, UserService userService) {
        this.bookingService = bookingService;
        this.tourService = tourService;
        this.userService = userService;
    }

    /**
     * Обрабатывает POST-запрос на бронирование тура.
     * Создает новую запись о бронировании, связывая текущего аутентифицированного
     * пользователя с выбранным туром, и сохраняет информацию в базе данных.
     *
     * @param tourId    идентификатор тура для бронирования
     * @param principal объект, содержащий информацию о текущем аутентифицированном пользователе
     * @return перенаправление на страницу профиля пользователя после успешного бронирования
     */
    @PostMapping("/{tourId}")
    public String bookTour(@PathVariable Long tourId, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        Tour tour = tourService.findById(tourId);

        Booking booking = Booking.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .tourId(tour.getId())
                .tourName(tour.getName())
                .country(tour.getCountry())
                .type(tour.getType())
                .price(tour.getPrice())
                .startDate(tour.getStartDate())
                .endDate(tour.getEndDate())
                .imagePath(tour.getImagePath())
                .description(tour.getDescription())
                .bookingDate(LocalDateTime.now())
                .build();

        bookingService.save(booking);
        return "redirect:/profile";
    }
}
