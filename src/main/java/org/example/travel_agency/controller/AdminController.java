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

    @PostMapping("/tour/add")
    public String addTour(@ModelAttribute Tour tour) {
        tourService.save(tour);
        return "redirect:/admin";
    }
}
