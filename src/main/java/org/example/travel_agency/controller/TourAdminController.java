package org.example.travel_agency.controller;

import org.example.travel_agency.model.Tour;
import org.example.travel_agency.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Контроллер для административного управления турами.
 * Предоставляет функциональность для создания, редактирования и удаления туров,
 * а также для загрузки и сохранения изображений туров.
 * Доступ к этим маршрутам требует наличия прав администратора,
 * что настроено в {@link org.example.travel_agency.config.SecurityConfig}.
 */
@Controller
@RequestMapping("/admin/tours")
public class TourAdminController {

    /**
     * Сервис для работы с данными о турах.
     */
    private final TourService tourService;

    /**
     * Создает новый экземпляр контроллера администрирования туров с необходимым сервисом.
     *
     * @param tourService сервис для управления турами
     */
    @Autowired
    public TourAdminController(TourService tourService) {
        this.tourService = tourService;
    }

    /**
     * Обрабатывает GET-запрос к странице управления турами.
     * Загружает список всех туров и создает пустой объект тура для формы создания.
     *
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона Thymeleaf "admin-tours" для отображения страницы управления турами
     */
    @GetMapping
    public String manageTours(Model model) {
        model.addAttribute("tours", tourService.findAll());
        model.addAttribute("tour", new Tour());
        return "admin-tours";
    }

    /**
     * Обрабатывает POST-запрос на удаление тура.
     * Удаляет тур из базы данных по указанному идентификатору.
     *
     * @param id идентификатор тура для удаления
     * @return перенаправление на страницу управления турами после удаления
     */
    @PostMapping("/delete/{id}")
    public String deleteTour(@PathVariable Long id) {
        tourService.deleteById(id);
        return "redirect:/admin/tours";
    }

    /**
     * Обрабатывает GET-запрос на редактирование тура.
     * Загружает данные выбранного тура в форму для редактирования.
     *
     * @param id идентификатор тура для редактирования
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона Thymeleaf "admin-tours" для отображения страницы редактирования тура
     */
    @GetMapping("/edit/{id}")
    public String editTour(@PathVariable Long id, Model model) {
        Tour tour = tourService.findById(id);
        model.addAttribute("tour", tour);
        model.addAttribute("tours", tourService.findAll());
        return "admin-tours";
    }

    /**
     * Обрабатывает POST-запрос на сохранение тура.
     * Сохраняет загруженное изображение и создает новый или обновляет существующий тур.
     * Изображение сохраняется в директории "uploads/" с уникальным именем файла.
     *
     * @param tour объект тура с данными из формы
     * @param imageFile загруженный файл изображения для тура
     * @return перенаправление на страницу управления турами после сохранения
     * @throws IOException если возникла ошибка при сохранении файла изображения
     */
    @PostMapping("/save")
    public String saveTour(@ModelAttribute Tour tour, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        String uploadDir = "uploads/";
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);
        Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        tour.setImagePath("/uploads/" + fileName);

        if (tour.getId() != null && tourService.findById(tour.getId()) != null) {
            Tour existing = tourService.findById(tour.getId());
            existing.setName(tour.getName());
            existing.setCountry(tour.getCountry());
            existing.setType(tour.getType());
            existing.setPrice(tour.getPrice());
            existing.setStartDate(tour.getStartDate());
            existing.setEndDate(tour.getEndDate());
            existing.setDescription(tour.getDescription());
            existing.setImagePath(tour.getImagePath());
            tourService.save(existing);
        } else {
            tour.setId(null);
            tourService.save(tour);
        }
        return "redirect:/admin/tours";
    }
}
