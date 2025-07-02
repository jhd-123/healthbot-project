package com.example.demo.controller;

import com.example.demo.dto.KakaoPlaceDto;
import com.example.demo.dto.KakaoSearchResponseDto;
import com.example.demo.service.KakaoLocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/kakao")
@RequiredArgsConstructor
public class KakaoLocalController {

    private final KakaoLocalService kakaoLocalService;

    /**
     * 사용자의 위도(lat), 경도(lon)를 기준으로 반경 2km 내의 헬스장을 검색한다.
     */
    @GetMapping("/gyms")	
    public List<KakaoPlaceDto> getNearbyGyms(@RequestParam double lat, @RequestParam double lon) {
        try {
            KakaoSearchResponseDto response = kakaoLocalService.searchGyms(lat, lon, 2000).block();
            return response != null ? response.getDocuments() : Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();  // 빈 리스트 반환으로 예외 상황 처리
        }
    }
}
