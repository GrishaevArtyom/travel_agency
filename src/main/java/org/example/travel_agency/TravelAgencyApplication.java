package org.example.travel_agency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения туристического агентства.
 * Этот класс инициализирует Spring Boot приложение и содержит
 * точку входа в программу.
 */
@SpringBootApplication
public class TravelAgencyApplication {

    /**
     * Точка входа в приложение.
     * Запускает Spring Boot приложение.
     *
     * @param args аргументы командной строки, переданные при запуске приложения
     */
    public static void main(String[] args) {
        SpringApplication.run(TravelAgencyApplication.class, args);
    }

}
