package org.example.travel_agency.controller;

import org.example.travel_agency.model.Tour;
import org.example.travel_agency.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Контроллер для управления отображением информации о турах.
 * Предоставляет функциональность для просмотра списка туров с фильтрацией
 * и детальной информации о конкретном туре.
 * Маршруты, обрабатываемые данным контроллером, доступны всем пользователям,
 * что настроено в {@link org.example.travel_agency.config.SecurityConfig}.
 */
@Controller
public class TourController {

    /**
     * Сервис для работы с данными о турах.
     */
    private final TourService tourService;

    /**
     * Создает новый экземпляр контроллера туров с необходимым сервисом.
     *
     * @param tourService сервис для получения информации о турах
     */
    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    /**
     * Обрабатывает GET-запрос к странице списка туров.
     * Поддерживает фильтрацию туров по различным параметрам и сортировку результатов.
     * Рассчитывает минимальную и максимальную цены для отображения на странице.
     *
     * @param country  страна назначения для фильтрации (опционально)
     * @param type     тип тура для фильтрации (опционально)
     * @param startFrom минимальная дата начала тура (опционально)
     * @param startTo   максимальная дата начала тура (опционально)
     * @param priceFrom минимальная цена тура (опционально)
     * @param priceTo   максимальная цена тура (опционально)
     * @param sort      параметр сортировки результатов (опционально)
     * @param model     объект модели для передачи данных в представление
     * @return имя шаблона Thymeleaf "tours" для отображения списка туров
     */
    @GetMapping("/tours")
    public String showTours(@RequestParam(required = false) String country,
                            @RequestParam(required = false) String type,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startFrom,
                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startTo,
                            @RequestParam(required = false) BigDecimal priceFrom,
                            @RequestParam(required = false) BigDecimal priceTo,
                            @RequestParam(required = false) String sort,
                            Model model) {

        List<Tour> tours = tourService.getFilteredTours(country, type, startFrom, startTo, priceFrom, priceTo, sort);
        model.addAttribute("tours", tours);

        model.addAttribute("tourCount", tours.size());

        if (!tours.isEmpty()) {
            BigDecimal minPrice = tours.stream()
                    .map(Tour::getPrice)
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);

            BigDecimal maxPrice = tours.stream()
                    .map(Tour::getPrice)
                    .max(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);

            model.addAttribute("minPrice", minPrice);
            model.addAttribute("maxPrice", maxPrice);
        }

        return "tours";
    }

    /**
     * Обрабатывает GET-запрос к странице с детальной информацией о конкретном туре.
     * Загружает данные о туре по его идентификатору и передает их в представление.
     *
     * @param id    идентификатор тура для отображения
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона Thymeleaf "tour-details" для отображения детальной информации о туре
     */
    @GetMapping("/tours/{id}")
    public String tourDetails(@PathVariable Long id, Model model) {
        Tour tour = tourService.findById(id);
        model.addAttribute("tour", tour);
        return "tour-details";
    }
}
