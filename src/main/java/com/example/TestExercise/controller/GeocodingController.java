package com.example.TestExercise.controller;

import com.example.TestExercise.dto.AddressDto;
import com.example.TestExercise.dto.CoordinatesDto;
import com.example.TestExercise.service.GeocodingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для геокодинга
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
public class GeocodingController {

    private final GeocodingService geocodingService;

    /**
     * Кодирует адрес в координаты
     * @param language который по умолчанию равен - "ru_RU" для установки языка кодирования,
     * @param address содержит в себе информацию об адресе для которого будет осуществялться геокодирование
     * @return координаты
     */
    @PostMapping("/geocoding")
    public CoordinatesDto straightGeocoding(@RequestParam(defaultValue = "ru_RU") String language,
                                                  @RequestParam String address) {
        log.info("Входные параметры lang - {}, address - {}", language, address);
        return geocodingService.encode(language, address);
    }

    /**
     * Декодирует координаты в адрес
     * @param language который по умолчанию равен - "ru_RU" для установки языка кодирования,
     * @param longitude содержит информацию о долготе адреса
     * @param latitude содержит информацию о широте адреса
     * @return
     */
    @GetMapping("/geocoding")
    public List<AddressDto> inverseConversion(@RequestParam(defaultValue = "ru_RU") String language,
                                        @RequestParam String longitude,
                                        @RequestParam String latitude) {
        log.info("Входные параметры lang - {}, longitude - {}, latitude - {}", language, longitude, latitude);
       return geocodingService.decode(language, longitude, latitude);
    }
}
