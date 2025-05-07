package org.example.travel_agency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Информация о пользователе
    private Long userId;
    private String username;

    // Полная информация о туре (без @ManyToOne)
    private Long tourId;
    private String tourName;
    private String country;
    private String type;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String imageUrl;
    private String description;

    @Column(nullable = false)
    private LocalDateTime bookingDate;
}