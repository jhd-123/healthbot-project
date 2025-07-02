package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ChatbotApiController {

    @PostMapping("/api/workout-routine")
    public ResponseEntity<Map<String, Object>> getWorkoutRoutine(@RequestBody Map<String, Object> payload) {

        // ✅ 카카오 챗봇 응답 포맷
        Map<String, Object> simpleText = new HashMap<>();
        simpleText.put("text", "✅ 월: 하체\n✅ 화: 가슴\n✅ 수: 등\n✅ 목: 어깨\n✅ 금: 팔\n✅ 토: 유산소\n✅ 일: 휴식");

        Map<String, Object> output = new HashMap<>();
        output.put("simpleText", simpleText);

        Map<String, Object> template = new HashMap<>();
        template.put("outputs", new Object[]{output});

        Map<String, Object> response = new HashMap<>();
        response.put("version", "2.0");
        response.put("template", template);

        return ResponseEntity.ok(response);
    }
}
