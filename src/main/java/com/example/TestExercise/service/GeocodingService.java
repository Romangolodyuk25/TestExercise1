package com.example.TestExercise.service;

import com.example.TestExercise.dto.AddressDto;
import com.example.TestExercise.dto.CoordinatesDto;

import java.util.List;

/**
 *
 */
public interface GeocodingService {

    /**
     * прямое преобразование ИЗ АДРЕСА В ДОЛГОТУ И ШИРОТУ
     * @param language
     * @param address
     * @return
     */
    List<CoordinatesDto> encode(String language, String address);


    /**
     * обратное преобразование ИЗ ШИРОТЫ И ДОЛГОТЫ В АДРЕС
     * @param language
     * @param latitude
     * @param longitude
     * @return
     */
    List<AddressDto> decode(String language, String longitude, String latitude);
}
