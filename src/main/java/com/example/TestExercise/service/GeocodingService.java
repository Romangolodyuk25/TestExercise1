package com.example.TestExercise.service;

import com.example.TestExercise.dto.AddressDto;
import com.example.TestExercise.dto.CoordinatesDto;

import java.util.List;

/**
 * Интерфейс, имплементация которого будет исопльзоваться для реализации
 * логики прямого геокодирования и обратного геокодирования
 */
public interface GeocodingService {

    /**
     * прямое преобразование ИЗ АДРЕСА В ДОЛГОТУ И ШИРОТУ
     * @param language - отображает язык на котором будет происходить обратное геокодирование
     * @param address - отображает Адресс по которому будет происходить обратное геокодирование
     * @return - возвращается список Дто Координат в которых отображены Широта
     * и Долгота адреса по коотрому происходило геокодирование
     */
    List<CoordinatesDto> encode(String language, String address);


    /**
     * обратное преобразование ИЗ ШИРОТЫ И ДОЛГОТЫ В АДРЕС
     * @param language - отображает язык на котором будет происходить прямое геокодирование
     * @param latitude - отображается широту
     * @param longitude - отоброжает долготу
     * @return - возвращается список Дто Адрессов в которых отображена информация об адрессе поиска
     */
    List<AddressDto> decode(String language, String longitude, String latitude);
}
