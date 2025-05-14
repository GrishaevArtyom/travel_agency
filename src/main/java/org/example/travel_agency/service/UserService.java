package org.example.travel_agency.service;

import org.example.travel_agency.model.User;
import org.example.travel_agency.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для работы с пользователями.
 * Предоставляет бизнес-логику для управления сущностями {@link User}.
 */
@Service
public class UserService {

    /**
     * Репозиторий для доступа к данным пользователей.
     */
    private final UserRepository userRepository;

    /**
     * Создает новый экземпляр сервиса пользователей с указанным репозиторием.
     *
     * @param userRepository репозиторий для доступа к данным пользователей
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Получает список всех пользователей.
     *
     * @return список всех пользователей
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Сохраняет пользователя в базе данных.
     * Может использоваться как для создания нового пользователя, так и для обновления существующего.
     *
     * @param user пользователь для сохранения
     */
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя, которого необходимо удалить
     */
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Обновляет роль пользователя.
     *
     * @param userId идентификатор пользователя, чью роль нужно обновить
     * @param roleName новое название роли
     * @throws IllegalArgumentException если пользователь с указанным идентификатором не найден
     */
    public void updateUserRole(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        user.setRole(roleName);
        userRepository.save(user);
    }

    /**
     * Находит пользователя по имени пользователя.
     *
     * @param username имя пользователя для поиска
     * @return найденный пользователь или null, если пользователь не найден
     */
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
