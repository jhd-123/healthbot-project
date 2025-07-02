package com.example.demo.dto;

import java.util.List;

public class KakaoSearchResponseDto {
    private List<KakaoPlaceDto> documents;

    public List<KakaoPlaceDto> getDocuments() {
        return documents;
    }

    public void setDocuments(List<KakaoPlaceDto> documents) {
        this.documents = documents;
    }
}