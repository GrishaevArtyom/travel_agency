package org.example.travel_agency.service;

import org.example.travel_agency.model.User;
import org.example.travel_agency.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация интерфейса UserDetailsService для аутентификации пользователей.
 * Предоставляет метод для загрузки пользовательских данных из репозитория
 * и преобразования их в формат, понятный Spring Security.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Репозиторий для доступа к данным пользователей.
     */
    private final UserRepository userRepository;

    /**
     * Создает новый экземпляр сервиса аутентификации с указанным репозиторием пользователей.
     *
     * @param userRepository репозиторий для доступа к данным пользователей
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Загружает пользовательские данные по имени пользователя.
     * Используется Spring Security для аутентификации пользователя.
     *
     * @param username имя пользователя, данные которого необходимо загрузить
     * @return объект UserDetails с информацией о пользователе для Spring Security
     * @throws UsernameNotFoundException если пользователь с указанным именем не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}
