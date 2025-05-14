package org.example.travel_agency.repository;

import org.example.travel_agency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link User}.
 * Предоставляет методы для выполнения операций с данными пользователей в базе данных.
 * Наследует базовый функционал JpaRepository для выполнения CRUD-операций.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по имени пользователя.
     *
     * @param username имя пользователя для поиска
     * @return Optional, содержащий найденного пользователя, или пустой Optional, если пользователь не найден
     */
    Optional<User> findByUsername(String username);
}
