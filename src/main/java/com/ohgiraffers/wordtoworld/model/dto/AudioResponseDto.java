package com.ohgiraffers.wordtoworld.model.dto;

public class AudioResponseDto {

    public AudioResponseDto(String result_text) {
        this.result_text = result_text;
    }

    private String result_text;

    public String getResult_text() {
        return result_text;
    }

    public void setResult_text(String result_text) {
        this.result_text = result_text;
    }
}
