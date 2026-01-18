package com.mariofan.wordcreator.services.exceptions;

import com.mariofan.wordcreator.services.ElementDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {

        String requestBody = getString(ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Неверный значение поля")
                .details(
                        "Получено значение: " + requestBody
                )
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false))
                .build();

        log.error("Ошибка парсинга JSON. Тело запроса: {}", requestBody, ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private static String getString(HttpMessageNotReadableException ex) {
        String requestBody = "Неизвестно";

        Throwable rootCause = ex.getRootCause();
        if (rootCause != null) {
            requestBody = rootCause.getMessage();
        }

        if (ex.getMessage() != null && ex.getMessage().contains("JSON parse error")) {
            String[] parts = ex.getMessage().split("\"");
            if (parts.length > 1) {
                requestBody = parts[parts.length - 2];
            }
        }
        return requestBody;
    }

}
