package com.example.TestExercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект предназначеный для того что бы вернуть пользователю информацию в случае ошибки
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    String message;
    String reason;
    String status;
    String timestamp;
}
