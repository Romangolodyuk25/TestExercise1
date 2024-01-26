package com.example.TestExercise.service;

import com.example.TestExercise.dto.AddressDto;
import com.example.TestExercise.dto.CoordinatesDto;
import com.example.TestExercise.dto.GeocoderResponse;
import com.example.TestExercise.model.Cash;
import com.example.TestExercise.repository.CashRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GeocodingServiceImpl implements GeocodingService {

    private static final String API_KEY = "691c961c-4925-4cfc-831b-376aa904bd6c";
    private static final String PATH = "https://geocode-maps.yandex.ru/1.x/?format=json&sco=longlat&apikey=";

    final private Map<String, String> cash = new HashMap<>();
    final private CashRepository cashRepository;

    @Override
    public List<CoordinatesDto> encode(String language, String address) {
        List<Cash> cashList = cashRepository.findByAddress(address);
        if (cashList.size() != 0) {
            log.info("Найдено {} количесвто совпадений в кэше ", cashList.size());
            return cashList.stream()
                    .map(c -> c.getCoordinates().split(","))
                    .map(c -> new CoordinatesDto(c[0], c[1]))
                    .collect(Collectors.toList());
        }

        String url = PATH + API_KEY + "&lang=" + language + "&geocode=" + address;
        RestTemplate restTemplate = new RestTemplate();

        GeocoderResponse response = restTemplate.getForObject(url, GeocoderResponse.class);
        //сделать что бы работало не с 1 значением а со списком(убрать .get(0))
        List<String> result = response.getResponse().getGeoObjectCollection().getFeatureMember().stream()
                .map(o -> o.getGeoObject().getPoint().getPos())
                .collect(Collectors.toList());

        List<CoordinatesDto> coordinatesDtos = result.stream()
                .map(i -> i.split(" "))
                .map(i -> new CoordinatesDto(i[0], i[1]))
                .collect(Collectors.toList());

        String lon = coordinatesDtos.get(0).getLongitude();
        String lat = coordinatesDtos.get(0).getLatitude();
        Cash cash = new Cash(null, lon + "," + lat, address);
        cashRepository.save(cash);

        return coordinatesDtos;
    }

    @Override
    public List<AddressDto> decode(String language, String longitude, String latitude) {

        List<Cash> cashList = cashRepository.findByCoordinates(longitude + "," + latitude);

        if (cashList.size() != 0) {
            log.info("Найдено {} количесвто совпадений в кэше ", cashList.size());
            return cashList.stream()
                    .map(c -> new AddressDto(c.getAddress()))
                    .collect(Collectors.toList());
        }
        String url = PATH + API_KEY + "&lang=" + language + "&geocode=" + longitude + "," + latitude;
        RestTemplate restTemplate = new RestTemplate();

        GeocoderResponse response = restTemplate.getForObject(url, GeocoderResponse.class);
        //сделать что бы работало не с 1 значением а со списком(убрать .get(0))
        List<String> result = response.getResponse().getGeoObjectCollection().getFeatureMember().stream()
                .map(s -> s.getGeoObject().getMetaDataProperty().getGeocoderMetaData().getText())
                .collect(Collectors.toList());

        List<AddressDto> addressDtos = result.stream()
                .map(AddressDto::new)
                .collect(Collectors.toList());

        for(AddressDto a : addressDtos) {
            Cash cash = new Cash(null, longitude + "," + latitude, a.getAddress());
            cashRepository.save(cash);
        }

        return addressDtos;
    }
}
