package org.example.travel_agency.controller;

import org.example.travel_agency.model.Booking;
import org.example.travel_agency.model.User;
import org.example.travel_agency.service.BookingService;
import org.example.travel_agency.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final BookingService bookingService;
    private final UserService userService;

    public ProfileController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping
    public String userProfile(Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        List<Booking> bookings = bookingService.getBookingsByUsername(user.getUsername());
        model.addAttribute("bookings", bookings);
        return "profile";
    }

    @PostMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, Principal principal) {
        Booking booking = bookingService.findById(id);

        if (booking != null && booking.getUsername().equals(principal.getName())) {
            bookingService.deleteBooking(id);
        }

        return "redirect:/profile";
    }
}
