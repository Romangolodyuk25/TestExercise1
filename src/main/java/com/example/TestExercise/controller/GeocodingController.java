package com.example.TestExercise.controller;

import com.example.TestExercise.dto.AddressDto;
import com.example.TestExercise.dto.CoordinatesDto;
import com.example.TestExercise.service.GeocodingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
public class GeocodingController {

    private final GeocodingService geocodingService;

    @PostMapping("/geocoding")
    public List<CoordinatesDto> straightGeocoding(@RequestParam(defaultValue = "ru_RU") String language,
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
