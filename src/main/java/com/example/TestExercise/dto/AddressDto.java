package com.example.TestExercise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO объект котоырй является рузультатом обратного геокодирования,
 * предназначеный для того что бы вернуть пользователю информацию об адресе
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String address;
}
