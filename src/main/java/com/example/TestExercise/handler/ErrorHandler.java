package com.example.TestExercise.handler;

import com.example.TestExercise.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс-Обработчик, предназначенный для обработки исключений и передачи корректного ответа с определенныи статус кодом
 */
@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse throwableHandler(Throwable e) {
        log.error("500 {}", e.getMessage(), e);
        String stackTrace = getStackTrace(e);
        return new ErrorResponse(e.getMessage(), stackTrace, HttpStatus.BAD_REQUEST.name(), LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final ValidationException e) {
        log.error("Ошибка валидации {}",e.getMessage(), e);
        String stackTrace = getStackTrace(e);
        return new ErrorResponse(e.getMessage(), stackTrace, HttpStatus.BAD_REQUEST.name(), LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        log.error("Ошибка валидации {}",e.getMessage(), e);
        String stackTrace = getStackTrace(e);
        return new ErrorResponse(e.getMessage(), stackTrace, HttpStatus.BAD_REQUEST.name(), LocalDateTime.now().format(formatter));
    }

    private String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
