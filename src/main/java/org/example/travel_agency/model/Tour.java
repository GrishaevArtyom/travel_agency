package org.example.travel_agency.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int stock;
}
