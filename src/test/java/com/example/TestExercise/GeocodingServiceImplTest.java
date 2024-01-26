package com.example.TestExercise;

import com.example.TestExercise.dto.AddressDto;
import com.example.TestExercise.dto.CoordinatesDto;
import com.example.TestExercise.service.GeocodingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
public class GeocodingServiceImplTest {

    @Autowired
    private GeocodingService geocodingService;


    private String address = "Дубай, бульвар Мухаммед Бин Рашид, дом 1";
    private String longitude = "55.274247";
    private String latitude = "25.19718";
    private String language = "ru_RU";

    @Test
    public void shouldBeEncode() {
        CoordinatesDto receivedCoordinateDto = geocodingService.encode(language, address);
        assertNotNull(receivedCoordinateDto);
        assertEquals(receivedCoordinateDto.getLatitude(), latitude);
        assertEquals(receivedCoordinateDto.getLongitude(), longitude);
    }

    @Test
    public void shouldThrowValidationExceptionForEmptyAddressEncode() {
        assertThrows(ValidationException.class, () -> geocodingService.encode(language, ""));
    }

    @Test
    public void shouldThrowValidationExceptionForAddressEncodeIsBlank() {
        assertThrows(ValidationException.class, () -> geocodingService.encode(language, "     "));
    }

    @Test
    public void shouldBeDecode() {
        List<AddressDto> receivedAddressDto = geocodingService.decode(language, longitude, latitude);

        assertNotNull(receivedAddressDto);
        assertEquals(7, receivedAddressDto.size());
    }

    @Test
    public void shouldThrowValidationExceptionForLongitudeIsEmptyDecode() {
        assertThrows(ValidationException.class, () -> geocodingService.decode(language, "", latitude));
    }

    @Test
    public void shouldThrowValidationExceptionForLatitudeIsEmptyDecode() {
        assertThrows(ValidationException.class, () -> geocodingService.decode(language, longitude, ""));
    }

    @Test
    public void shouldThrowValidationExceptionForLongitudeIsBlankDecode() {
        assertThrows(ValidationException.class, () -> geocodingService.decode(language, "      ", latitude));
    }

    @Test
    public void shouldThrowValidationExceptionForLatitudeIsBlankDecode() {
        assertThrows(ValidationException.class, () -> geocodingService.decode(language, longitude, "     "));
    }

    @Test
    public void shouldThrowValidationExceptionForLongitudeWrongLengthDecode() {
        assertThrows(ValidationException.class, () -> geocodingService.decode(language, "123.456789011.2213123761.23612378123", latitude));
    }

    @Test
    public void shouldThrowValidationExceptionForLatitudeWrongLengthDecode() {
        assertThrows(ValidationException.class, () -> geocodingService.decode(language, longitude, "123.456789011.2213123761.23612378123"));
    }
}
