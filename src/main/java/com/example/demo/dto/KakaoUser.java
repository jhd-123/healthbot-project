package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUser {
    private Long id;
    private String nickname;
    private String email;
    private String name;
    private String gender;
    private String birthyear;
    private String phoneNumber;

    public KakaoUser(Long id, String nickname, String email, String name,
                     String gender, String birthyear, String phoneNumber) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.birthyear = birthyear;
        this.phoneNumber = phoneNumber;
    }

    // Getter, Setter, toString() 생략
}
