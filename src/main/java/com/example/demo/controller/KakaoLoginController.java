package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.KakaoUser;
import com.example.demo.service.KakaoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/oauth/kakao")
public class KakaoLoginController {

    @Value("${kakao.rest-api-key}")
    private String restApiKey;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final KakaoService kakaoService;

    public KakaoLoginController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @GetMapping("/callback")
    public String kakaoCallback(@RequestParam("code") String code, HttpSession session) {
        String accessToken = kakaoService.getAccessToken(code);
        KakaoUser kakaoUser = kakaoService.getUserInfo(accessToken);

        session.setAttribute("loginUser", kakaoUser);
        System.out.println("✅ 로그인 완료: " + kakaoUser);

        // ✅ 콘솔에 사용자 전체 정보 출력
        System.out.println("✅ 카카오 로그인 완료:");
        System.out.println("ID: " + kakaoUser.getId());
        System.out.println("닉네임: " + kakaoUser.getNickname());
        System.out.println("이메일: " + kakaoUser.getEmail());
        System.out.println("이름: " + kakaoUser.getName());
        System.out.println("성별: " + kakaoUser.getGender());
        System.out.println("출생년도: " + kakaoUser.getBirthyear());
        System.out.println("전화번호: " + kakaoUser.getPhoneNumber());
        // ✅ 로그인 후 챗봇 페이지로 이동
        return "redirect:/chatbot";
    }
}
