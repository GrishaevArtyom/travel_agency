package org.example.travel_agency.controller;

import org.example.travel_agency.model.Tour;
import org.example.travel_agency.model.User;
import org.example.travel_agency.service.TourService;
import org.example.travel_agency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public String adminPanel(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("tours", tourService.findAll());
        model.addAttribute("tour", new Tour());
        return "admin";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/tour/delete/{id}")
    public String deleteTour(@PathVariable Long id) {
        tourService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/user/update-role/{id}")
    public String updateUserRole(@PathVariable Long id, @RequestParam String role) {
        userService.updateUserRole(id, role);
        return "redirect:/admin";
    }

    @GetMapping("/tour/edit/{id}")
    public String editTour(@PathVariable Long id, Model model) {
        Tour tour = tourService.findById(id);
        model.addAttribute("tour", tour);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("tours", tourService.findAll());
        return "admin";
    }

    @PostMapping("/tour/save")
    public String saveTour(@ModelAttribute Tour tour) {
        if (tour.getId() != null && tourService.findById(tour.getId()) != null) {
            // обновляем существующий
            Tour existing = tourService.findById(tour.getId());
            existing.setName(tour.getName());
            existing.setCountry(tour.getCountry());
            existing.setType(tour.getType());
            existing.setPrice(tour.getPrice());
            existing.setStartDate(tour.getStartDate());
            existing.setEndDate(tour.getEndDate());
            existing.setDescription(tour.getDescription());
            existing.setImageUrl(tour.getImageUrl());
            tourService.save(existing);
        } else {
            // новый тур
            tour.setId(null); // на всякий случай, чтобы точно не дублировался
            tourService.save(tour);
        }
        return "redirect:/admin";
    }



}
