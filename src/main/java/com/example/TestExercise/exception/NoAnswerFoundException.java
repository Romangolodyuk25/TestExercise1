package com.example.TestExercise.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс, предназначенный для выброса непроверяемого Исключения в случае, если ответа не будет найдено.
 */
@Getter
@Setter
public class NoAnswerFoundException extends RuntimeException {
    public NoAnswerFoundException(String message) {
        super(message);
    }
}
