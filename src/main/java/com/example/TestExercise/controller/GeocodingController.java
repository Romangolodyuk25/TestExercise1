package com.example.TestExercise.controller;

import com.example.TestExercise.dto.AddressDto;
import com.example.TestExercise.dto.CoordinatesDto;
import com.example.TestExercise.service.GeocodingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер с двумя едпоинтами
 * 1. HTTP метод POST straightGeocoding с 2 параметрами:
 * - String language, который по умолчанию равен - "ru_RU" для установки языка кодирования,
 * - String address содержит в себе информацию об адресе для которого будет осуществялться геокодирование
 * метод осуществляет прямое геокодирование(из адреса в долготу и широту)
 *
 * 2. HTTP метод GET inverseConversion с 3 параметрами:
 *  - String language, который по умолчанию равен - "ru_RU" для установки языка кодирования,
 *  - String longitude содержит информацию о долготе адреса
 *  - String latitude содержит информацию о широте адреса
 *  метод осуществляет обратное геокодирования(из долготы и широты в адрес)
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
public class GeocodingController {

    private final GeocodingService geocodingService;

    @PostMapping("/geocoding")
    public CoordinatesDto straightGeocoding(@RequestParam(defaultValue = "ru_RU") String language,
                                                  @RequestParam String address) {
        log.info("Входные параметры lang - {}, address - {}", language, address);
        return geocodingService.encode(language, address);
    }

    @GetMapping("/geocoding")
    public List<AddressDto> inverseConversion(@RequestParam(defaultValue = "ru_RU") String language,
                                        @RequestParam String longitude,
                                        @RequestParam String latitude) {
        log.info("Входные параметры lang - {}, longitude - {}, latitude - {}", language, longitude, latitude);
       return geocodingService.decode(language, longitude, latitude);
    }
}
