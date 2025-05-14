package com.ohgiraffers.wordtoworld.model.dto;

// ai로 보낸 wav 파일을 텍스트로 받아옴
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
