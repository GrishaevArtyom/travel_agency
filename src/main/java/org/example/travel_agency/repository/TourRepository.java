package org.example.travel_agency.repository;

import org.example.travel_agency.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link Tour}.
 * Предоставляет методы для выполнения операций с данными туров в базе данных.
 * Наследует базовый функционал JpaRepository для выполнения CRUD-операций.
 */
@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
}
