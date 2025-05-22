package org.example.travel_agency.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.HashMap;

/**
 * REST-контроллер для предоставления данных об авторе.
 * Демонстрирует работу REST API для получения информационных данных.
 */
@RestController
@RequestMapping("/api")
public class AboutRestController {

    /**
     * Возвращает информацию об авторе в формате JSON.
     *
     * @return Map с данными об авторе
     */
    @GetMapping("/author-info")
    public Map<String, String> getAuthorInfo() {
        Map<String, String> authorInfo = new HashMap<>();
        authorInfo.put("university", "Финансовый университет при Правительстве РФ");
        authorInfo.put("course", "2");
        authorInfo.put("specialization", "Программная инженерия");
        authorInfo.put("group", "ТРПО23-4");
        authorInfo.put("student", "Гришаев Артём Сергеевич");
        authorInfo.put("theme", "Информационно-справочная система туристического агентства");
        return authorInfo;
    }
}
