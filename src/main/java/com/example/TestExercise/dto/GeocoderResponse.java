package com.example.TestExercise.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

/**
 * DTO объект - результат ответа сервера на запрос с адресом или координатами.
 * Класс содержит в себе 7 inner-классов для выделения сущностей,
 * которые неразрыно связаны с сущностю GeocoderResponse
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeocoderResponse {
    public Response response;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        @JsonProperty("GeoObjectCollection")
        private GeoObjectCollection geoObjectCollection;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeoObjectCollection {
        private ArrayList<FeatureMember> featureMember;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureMember {
        @JsonProperty("GeoObject")
        private GeoObject geoObject;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MetaDataProperty {
        @JsonProperty("GeocoderMetaData")
        private GeocoderMetaData geocoderMetaData;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeoObject {
        @JsonProperty("Point")
        private Point point;
        private MetaDataProperty metaDataProperty;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Point {
        private String pos;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GeocoderMetaData {
        private String text;
    }
}
