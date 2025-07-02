package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatbotController {

    @GetMapping("/chatbot")
    public String chatbot() {
        System.out.println(">>> /chatbot 요청 진입 - 로그인 체크 없이 바로 챗봇 페이지 반환");
        return "gyms/chatbot"; // /WEB-INF/views/gyms/chatbot.jsp
    }
}
