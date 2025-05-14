package com.ohgiraffers.wordtoworld.model.dto;

public class AudioRequestDto {

    private String audioUrl;

    public AudioRequestDto(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}
