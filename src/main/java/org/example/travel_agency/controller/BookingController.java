package org.example.travel_agency.controller;

import org.example.travel_agency.model.Booking;
import org.example.travel_agency.model.Tour;
import org.example.travel_agency.model.User;
import org.example.travel_agency.service.BookingService;
import org.example.travel_agency.service.TourService;
import org.example.travel_agency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final TourService tourService;
    private final UserService userService;

    @Autowired
    public BookingController(BookingService bookingService, TourService tourService, UserService userService) {
        this.bookingService = bookingService;
        this.tourService = tourService;
        this.userService = userService;
    }

    @PostMapping("/{tourId}")
    public String bookTour(@PathVariable Long tourId,
                           @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findUserByUsername(currentUser.getUsername());
        Tour tour = tourService.findById(tourId);

        Booking booking = Booking.builder()
                .user(user)
                .tour(tour)
                .bookingDate(LocalDateTime.now())
                .build();

        bookingService.save(booking);
        return "redirect:/profile";
    }
}