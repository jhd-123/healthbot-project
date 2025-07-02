package com.example.demo.dto;

public class KakaoUser {
    private Long id;
    private String nickname;
    private String email;

    public KakaoUser(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }
    
    @Override
    public String toString() {
        return "KakaoUser{id=" + id + ", nickname='" + nickname + "', email='" + email + "'}";
    }
}