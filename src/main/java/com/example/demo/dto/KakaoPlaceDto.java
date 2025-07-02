package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPlaceDto {

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("road_address_name")
    private String roadAddressName;

    private String phone;
    private String x; // 경도
    private String y; // 위도
}
