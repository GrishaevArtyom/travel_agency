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

@Controller
public class TourController {

    private final TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

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

    @GetMapping("/tours/{id}")
    public String tourDetails(@PathVariable Long id, Model model) {
        Tour tour = tourService.findById(id);
        model.addAttribute("tour", tour);
        return "tour-details";
    }
}
