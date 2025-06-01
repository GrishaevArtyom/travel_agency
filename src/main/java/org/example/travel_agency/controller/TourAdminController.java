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
     * Директория для загрузки файлов изображений.
     */
    private static final String UPLOAD_DIR = "uploads/";

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
     * @return имя шаблона Thymeleaf "admin_tours" для отображения страницы управления турами
     */
    @GetMapping
    public String manageTours(Model model) {
        model.addAttribute("tours", tourService.findAll());
        model.addAttribute("tour", new Tour());
        return "admin_tours";
    }

    /**
     * Обрабатывает POST-запрос на удаление тура.
     * Удаляет тур из базы данных по указанному идентификатору,
     * а также удаляет связанный файл изображения из файловой системы.
     *
     * @param id идентификатор тура для удаления
     * @return перенаправление на страницу управления турами после удаления
     */
    @PostMapping("/delete/{id}")
    public String deleteTour(@PathVariable Long id) {
        Tour tour = tourService.findById(id);

        if (tour != null && tour.getImagePath() != null && !tour.getImagePath().isEmpty()) {
            try {
                String relativePath = tour.getImagePath().startsWith("/")
                    ? tour.getImagePath().substring(1)
                    : tour.getImagePath();

                Path fileToDelete = Paths.get(relativePath);
                if (Files.exists(fileToDelete)) {
                    Files.delete(fileToDelete);
                }
            } catch (IOException ignored) {
            }
        }

        tourService.deleteById(id);
        return "redirect:/admin/tours";
    }

    /**
     * Обрабатывает GET-запрос на редактирование тура.
     * Загружает данные выбранного тура в форму для редактирования.
     *
     * @param id идентификатор тура для редактирования
     * @param model объект модели для передачи данных в представление
     * @return имя шаблона Thymeleaf "admin_tours" для отображения страницы редактирования тура
     */
    @GetMapping("/edit/{id}")
    public String editTour(@PathVariable Long id, Model model) {
        Tour tour = tourService.findById(id);
        model.addAttribute("tour", tour);
        model.addAttribute("tours", tourService.findAll());
        return "admin_tours";
    }

    /**
     * Обрабатывает POST-запрос на сохранение тура.
     * Сохраняет загруженное изображение, если оно было предоставлено, и создает
     * новый или обновляет существующий тур. При редактировании существующего тура
     * позволяет сохранить текущее изображение, если новое не было загружено.
     * При замене изображения удаляет старый файл.
     *
     * @param tour объект тура с данными из формы
     * @param imageFile загруженный файл изображения для тура (может быть пустым при редактировании)
     * @param currentImagePath текущий путь к изображению (для существующих туров)
     * @return перенаправление на страницу управления турами после сохранения
     * @throws IOException если возникла ошибка при сохранении файла изображения
     */
    @PostMapping("/save")
    public String saveTour(
            @ModelAttribute Tour tour,
            @RequestParam(required = false) MultipartFile imageFile,
            @RequestParam(required = false) String currentImagePath) throws IOException {

        if (tour.getId() != null && imageFile != null && !imageFile.isEmpty() &&
            currentImagePath != null && !currentImagePath.isEmpty()) {
            try {
                String relativePath = currentImagePath.startsWith("/")
                    ? currentImagePath.substring(1)
                    : currentImagePath;

                Path oldFile = Paths.get(relativePath);
                if (Files.exists(oldFile)) {
                    Files.delete(oldFile);
                }
            } catch (IOException ignored) {
            }
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            tour.setImagePath("/uploads/" + fileName);
        } else if (tour.getId() != null) {
            tour.setImagePath(currentImagePath);
        }

        tourService.save(tour);

        return "redirect:/admin/tours";
    }
}
