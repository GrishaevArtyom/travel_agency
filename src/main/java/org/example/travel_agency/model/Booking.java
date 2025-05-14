package org.example.travel_agency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Класс Booking представляет сущность бронирования тура пользователем.
 * Содержит информацию о пользователе, забронированном туре и деталях бронирования.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    /**
     * Уникальный идентификатор бронирования.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор пользователя, совершившего бронирование.
     */
    @Column(nullable = false)
    private Long userId;

    /**
     * Имя пользователя, совершившего бронирование.
     */
    @Column(nullable = false)
    private String username;

    /**
     * Идентификатор забронированного тура.
     */
    @Column(nullable = false)
    private Long tourId;

    /**
     * Название забронированного тура.
     */
    @Column(nullable = false)
    private String tourName;

    /**
     * Страна, в которой проводится забронированный тур.
     */
    @Column(nullable = false)
    private String country;

    /**
     * Тип забронированного тура (например, пляжный, экскурсионный).
     */
    @Column(nullable = false)
    private String type;

    /**
     * Цена забронированного тура.
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Дата начала забронированного тура.
     */
    @Column(nullable = false)
    private LocalDate startDate;

    /**
     * Дата окончания забронированного тура.
     */
    @Column(nullable = false)
    private LocalDate endDate;

    /**
     * Путь к изображению забронированного тура.
     */
    @Column(nullable = false)
    private String imagePath;

    /**
     * Описание забронированного тура.
     */
    @Column(nullable = false)
    private String description;

    /**
     * Дата и время создания бронирования.
     */
    @Column(nullable = false)
    private LocalDateTime bookingDate;
}
