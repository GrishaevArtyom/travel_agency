package org.example.travel_agency.service;

import org.example.travel_agency.model.Booking;
import org.example.travel_agency.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с бронированиями туров.
 * Предоставляет бизнес-логику для управления сущностями {@link Booking}.
 */
@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    /**
     * Создает новый экземпляр сервиса бронирований с указанным репозиторием.
     *
     * @param bookingRepository репозиторий для доступа к данным бронирований
     */
    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    /**
     * Находит бронирование по его идентификатору.
     *
     * @param id идентификатор бронирования
     * @return найденное бронирование или null, если бронирование не найдено
     */
    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    /**
     * Удаляет бронирование по его идентификатору.
     *
     * @param id идентификатор бронирования, которое необходимо удалить
     */
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    /**
     * Сохраняет бронирование в базе данных.
     * Может использоваться как для создания нового бронирования, так и для обновления существующего.
     *
     * @param booking бронирование для сохранения
     */
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    /**
     * Получает список всех бронирований для указанного пользователя.
     *
     * @param username имя пользователя, чьи бронирования необходимо найти
     * @return список бронирований пользователя
     */
    public List<Booking> getBookingsByUsername(String username) {
        return bookingRepository.findByUsername(username);
    }
}
