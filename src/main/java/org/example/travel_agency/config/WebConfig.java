package org.example.travel_agency.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Конфигурация веб-компонентов приложения.
 * Реализует интерфейс WebMvcConfigurer для настройки обработки веб-запросов
 * и предоставления доступа к статическим ресурсам.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Настраивает обработчики ресурсов для предоставления доступа к файлам,
     * хранящимся вне контекста приложения.
     * Обеспечивает доступ к загруженным файлам по URL-пути "/uploads/**",
     * отображая его на физическую директорию "file:uploads/" на сервере.
     *
     * @param registry реестр обработчиков ресурсов для настройки
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
