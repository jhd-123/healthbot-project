package com.example.demo.service;

import com.example.demo.config.KakaoConfig;
import com.example.demo.dto.KakaoSearchResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KakaoLocalService {

    private final WebClient webClient;

    @Autowired
    public KakaoLocalService(KakaoConfig kakaoConfig) {
        this.webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader("Authorization", kakaoConfig.getApiKey())
                .build();
    }

    public Mono<KakaoSearchResponseDto> searchGyms(double lat, double lon, int radius) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/search/keyword.json") // ✅ keyword 검색 API로 변경
                        .queryParam("query", "헬스장")
                        .queryParam("x", lon)
                        .queryParam("y", lat)
                        .queryParam("radius", radius)
                        .queryParam("sort", "distance")
                        .build())
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Kakao API 오류: " + body))
                )
                .bodyToMono(KakaoSearchResponseDto.class);
    }

}
