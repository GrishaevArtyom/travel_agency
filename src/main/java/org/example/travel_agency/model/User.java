package org.example.travel_agency.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс User представляет сущность пользователя системы.
 * Содержит информацию об учетных данных и роли пользователя.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя для входа в систему.
     * Должно быть уникальным.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Пароль пользователя.
     * Хранится в зашифрованном виде.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Роль пользователя в системе.
     * Определяет права доступа.
     */
    @Column(nullable = false)
    private String role;
}