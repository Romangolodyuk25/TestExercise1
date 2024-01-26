package com.example.TestExercise.service;

import com.example.TestExercise.dto.AddressDto;
import com.example.TestExercise.dto.CoordinatesDto;
import com.example.TestExercise.dto.GeocoderResponse;
import com.example.TestExercise.exception.NoAnswerFoundException;
import com.example.TestExercise.model.Cash;
import com.example.TestExercise.repository.CashRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс - сервис , в котором отражена бизнес логика работы с API
 * имеет 2 статических поля
 * 1 - Хранит в себе API - токен
 * 2 - Хранит в себе полный путь для взаимодейсвтия с API
 * Данный сервис реализует 2 метода encode и decode для прямого и обратного геокодирования
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GeocodingServiceImpl implements GeocodingService {

    private static final String API_KEY = "691c961c-4925-4cfc-831b-376aa904bd6c";
    private static final String PATH = "https://geocode-maps.yandex.ru/1.x/?format=json&sco=longlat&apikey=";

    final private CashRepository cashRepository;

    @Override
    public CoordinatesDto encode(String language, String address) {
        String[] position;
        checkValidationForEncode(address);
        Optional<Cash> receivedCash = cashRepository.findByAddress(address);

        if (receivedCash.isPresent()) {
            Cash cash = receivedCash.get();
            position = cash.getCoordinates().split(",");
        } else {
            String url = PATH + API_KEY + "&lang=" + language + "&geocode=" + address;
            RestTemplate restTemplate = new RestTemplate();

            GeocoderResponse response = restTemplate.getForObject(url, GeocoderResponse.class);

            if (response == null) {
                throw new NoAnswerFoundException("no answer found");
            }
            position = response.getResponse().getGeoObjectCollection()
                    .getFeatureMember().get(0).getGeoObject()
                    .getPoint().getPos().split(" ");

            Cash cash = new Cash(null, position[0] + "," + position[1], address);
            cashRepository.save(cash);
        }
        return new CoordinatesDto(position[0], position[1]);
    }

    @Override
    public List<AddressDto> decode(String language, String longitude, String latitude) {
        checkValidationForDecode(longitude, latitude);
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

        if (response == null) {
            throw new NoAnswerFoundException("no answer found");
        }
        List<String> result = response.getResponse().getGeoObjectCollection().getFeatureMember().stream()
                .map(s -> s.getGeoObject().getMetaDataProperty().getGeocoderMetaData().getText())
                .collect(Collectors.toList());

        List<AddressDto> addressDtos = result.stream()
                .map(AddressDto::new)
                .collect(Collectors.toList());

        for (AddressDto a : addressDtos) {
            Cash cash = new Cash(null, longitude + "," + latitude, a.getAddress());
            if (cashRepository.findByAddress(a.getAddress()).isEmpty()) {
                cashRepository.save(cash);
            }
        }
        return addressDtos;
    }

    private void checkValidationForEncode(String address) {
        log.info("Параметр address {}", address);
        if (address.isBlank() || address.isEmpty() || address.length() > 512) {
            throw new ValidationException("Ошибка валидации, неправильно передан address");
        }
    }

    private void checkValidationForDecode(String longitude, String latitude) {
        log.info("Параметр longitude {}, latitude {}", longitude, latitude);
        if (longitude.isEmpty() || longitude.isBlank() || longitude.length() > 16 ||
                latitude.isBlank() || latitude.isEmpty() || latitude.length() > 16) {
            throw new ValidationException("Ошибка валидации, неправильно передан longitude или latitude");
        }
    }

}
