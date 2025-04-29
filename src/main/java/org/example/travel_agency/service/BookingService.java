package org.example.travel_agency.service;

import org.example.travel_agency.model.Booking;
import org.example.travel_agency.model.User;
import org.example.travel_agency.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getBookingsForUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public void save(Booking booking) {
        bookingRepository.save(booking);
    }
}
