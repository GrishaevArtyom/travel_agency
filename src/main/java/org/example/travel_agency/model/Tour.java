package org.example.travel_agency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Класс Tour представляет сущность туристического тура.
 * Содержит информацию о названии, стране, типе, цене и других характеристиках тура.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tour {

    /**
     * Уникальный идентификатор тура.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название тура.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Страна, в которой проводится тур.
     */
    @Column(nullable = false)
    private String country;

    /**
     * Тип тура (например, пляжный, экскурсионный).
     */
    @Column(nullable = false)
    private String type;

    /**
     * Цена тура.
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Дата начала тура.
     */
    @Column(nullable = false)
    private LocalDate startDate;

    /**
     * Дата окончания тура.
     */
    @Column(nullable = false)
    private LocalDate endDate;

    /**
     * Путь к изображению тура.
     */
    @Column(nullable = false)
    private String imagePath;

    /**
     * Описание тура.
     */
    @Column(nullable = false)
    private String description;
}
