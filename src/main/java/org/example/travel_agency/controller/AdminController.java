package org.example.travel_agency.controller;

import org.example.travel_agency.model.Tour;
import org.example.travel_agency.service.TourService;
import org.example.travel_agency.service.UserService;
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

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final TourService tourService;

    @Autowired
    public AdminController(UserService userService, TourService tourService) {
        this.userService = userService;
        this.tourService = tourService;
    }

    // Стартовая страница админки с кнопками управления
    @GetMapping
    public String adminPanel() {
        return "admin"; // только кнопки "Управление пользователями", "Редактирование туров"
    }

    // Управление пользователями
    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin-users";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/user/update-role/{id}")
    public String updateUserRole(@PathVariable Long id, @RequestParam String role) {
        userService.updateUserRole(id, role);
        return "redirect:/admin/users";
    }

    // Управление турами
    @GetMapping("/tours")
    public String manageTours(Model model) {
        model.addAttribute("tours", tourService.findAll());
        model.addAttribute("tour", new Tour()); // для формы добавления
        return "admin-tours";
    }

    @PostMapping("/tour/delete/{id}")
    public String deleteTour(@PathVariable Long id) {
        tourService.deleteById(id);
        return "redirect:/admin/tours";
    }

    @GetMapping("/tour/edit/{id}")
    public String editTour(@PathVariable Long id, Model model) {
        Tour tour = tourService.findById(id);
        model.addAttribute("tour", tour);
        model.addAttribute("tours", tourService.findAll());
        return "admin-tours"; // редактирование тура также на admin-tours.html
    }

    @PostMapping("/tour/save")
    public String saveTour(@ModelAttribute Tour tour, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        // Папка для сохранения изображений
        String uploadDir = "uploads/";
        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);
        Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        tour.setImagePath("/uploads/" + fileName); // Сохраняем путь к изображению

        if (tour.getId() != null && tourService.findById(tour.getId()) != null) {
            Tour existing = tourService.findById(tour.getId());
            existing.setName(tour.getName());
            existing.setCountry(tour.getCountry());
            existing.setType(tour.getType());
            existing.setPrice(tour.getPrice());
            existing.setStartDate(tour.getStartDate());
            existing.setEndDate(tour.getEndDate());
            existing.setDescription(tour.getDescription());
            existing.setImagePath(tour.getImagePath()); // Обновляем изображение
            tourService.save(existing);
        } else {
            tour.setId(null);
            tourService.save(tour);
        }
        return "redirect:/admin/tours";
    }
}
