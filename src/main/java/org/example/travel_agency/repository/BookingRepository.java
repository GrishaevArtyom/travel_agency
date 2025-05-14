package org.example.travel_agency.repository;

import org.example.travel_agency.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link Booking}.
 * Предоставляет методы для выполнения операций с данными бронирований в базе данных.
 * Наследует базовый функционал JpaRepository для выполнения CRUD-операций.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Находит все бронирования, принадлежащие пользователю с указанным именем.
     *
     * @param username имя пользователя, чьи бронирования необходимо найти
     * @return список бронирований данного пользователя
     */
    List<Booking> findByUsername(String username);
}
