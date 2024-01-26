package com.example.TestExercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO объект, который является результатом прямого геокодирования
 * предназначеный для того что бы вернуть пользователю ответ с долготой и широтой адреса
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatesDto {
    private String longitude;
    private String latitude;
}
