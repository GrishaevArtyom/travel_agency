package org.example.travel_agency.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер для обработки ошибок приложения.
 * Перехватывает стандартные ошибки Spring Boot и отображает
 * пользовательскую страницу ошибки с дружественным сообщением.
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Обрабатывает все запросы, перенаправленные на /error.
     * Извлекает информацию об ошибке и добавляет её в модель для отображения.
     *
     * @param request HTTP-запрос, содержащий информацию об ошибке
     * @param model модель для передачи данных в представление
     * @return имя шаблона Thymeleaf "error" для отображения страницы ошибки
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = "Произошла ошибка при обработке запроса";

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            model.addAttribute("status", statusCode);

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorMessage = "Запрашиваемая страница не найдена";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorMessage = "У вас нет доступа к этой странице";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorMessage = "Внутренняя ошибка сервера";
            }
        }

        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
