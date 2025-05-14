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

@Controller
@RequestMapping("/admin/tours")
public class TourAdminController {

    private final TourService tourService;

    @Autowired
    public TourAdminController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public String manageTours(Model model) {
        model.addAttribute("tours", tourService.findAll());
        model.addAttribute("tour", new Tour());
        return "admin-tours";
    }

    @PostMapping("/delete/{id}")
    public String deleteTour(@PathVariable Long id) {
        tourService.deleteById(id);
        return "redirect:/admin/tours";
    }

    @GetMapping("/edit/{id}")
    public String editTour(@PathVariable Long id, Model model) {
        Tour tour = tourService.findById(id);
        model.addAttribute("tour", tour);
        model.addAttribute("tours", tourService.findAll());
        return "admin-tours";
    }

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
