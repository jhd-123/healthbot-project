package com.example.demo.service;

import com.example.demo.config.KakaoProperties;
import com.example.demo.dto.KakaoUser;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class KakaoService {

    // ✅ HTTP 요청을 위한 RestTemplate 객체 생성
    private final RestTemplate restTemplate = new RestTemplate();

    // ✅ application.properties에 등록된 카카오 설정값 주입
    private final KakaoProperties kakaoProperties;

    public KakaoService(KakaoProperties kakaoProperties) {
        this.kakaoProperties = kakaoProperties;
    }

    /**
     * 🔐 인가 코드(code)를 가지고 액세스 토큰 요청
     */
    public String getAccessToken(String code) {
        String url = "https://kauth.kakao.com/oauth/token";

        // ✅ HTTP 요청 헤더 설정 (폼 데이터 전송)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // ✅ 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoProperties.getRestApiKey()); // REST API 키
        params.add("redirect_uri", kakaoProperties.getRedirectUri()); // 리다이렉트 URI
        params.add("code", code); // 카카오에서 받은 인가 코드

        // ✅ 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // ✅ POST 요청 보내기
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(url, request, JsonNode.class);

        // ✅ 응답에서 access_token 추출
        return response.getBody().get("access_token").asText();
    }

    /**
     * 🙍 access_token을 이용하여 사용자 정보 요청
     */
    public KakaoUser getUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        // ✅ 요청 헤더 (Bearer 토큰 방식)
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // ✅ GET 요청 보내기
        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, request, Map.class);

        Map<String, Object> body = response.getBody();

        // ✅ 사용자 정보 추출
        Long id = ((Number) body.get("id")).longValue();
        Map<String, Object> kakaoAccount = (Map<String, Object>) body.get("kakao_account");
        String email = (String) kakaoAccount.get("email");

        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
        String nickname = (String) profile.get("nickname");

        // ✅ KakaoUser DTO로 반환
        return new KakaoUser(id, nickname, email);
    }
}
