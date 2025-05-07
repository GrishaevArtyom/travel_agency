package org.example.travel_agency.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/login", "/register", "/css/**")) // Игнорирование CSRF на этих путях
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/tours", "/tours/**", "/register", "/css/**", "/about").permitAll() // Главная страница доступна всем
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Только админ доступен к админ-панели
                        .anyRequest().authenticated() // Все остальные страницы требуют авторизации
                )
                .formLogin(form -> form
                        .loginPage("/login") // Страница логина
                        .defaultSuccessUrl("/", true) // После успешного логина — переходит на главную страницу
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // После выхода — перенаправление на главную страницу
                        .permitAll());

        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
