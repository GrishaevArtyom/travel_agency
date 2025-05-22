package org.example.travel_agency.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурация безопасности приложения.
 * Определяет настройки Spring Security, включая правила доступа к различным URL,
 * конфигурацию формы входа, выхода из системы и шифрование паролей.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Создает и настраивает цепочку фильтров безопасности для HTTP-запросов.
     * Определяет правила доступа к различным URL-адресам приложения,
     * настраивает страницу входа и перенаправление после успешной аутентификации.
     *
     * @param http объект HttpSecurity для настройки правил безопасности
     * @return настроенная цепочка фильтров безопасности
     * @throws Exception если происходит ошибка в процессе настройки
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/login", "/register", "/css/**"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/tours/**", "/register", "/css/**", "/uploads/**", "/js/**", "/about", "/api/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/profile/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll());

        return http.build();
    }

    /**
     * Создает кодировщик паролей для хеширования и проверки паролей пользователей.
     * Использует алгоритм BCrypt для надежного хеширования паролей.
     *
     * @return экземпляр BCryptPasswordEncoder для шифрования паролей
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
