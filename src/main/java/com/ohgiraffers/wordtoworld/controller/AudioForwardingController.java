package com.ohgiraffers.wordtoworld.controller;

import com.ohgiraffers.wordtoworld.model.dto.AudioRequestDto;
import com.ohgiraffers.wordtoworld.service.AudioForwardingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AudioForwardingController {

    private final AudioForwardingService audioForwardingService;

    public AudioForwardingController(AudioForwardingService audioForwardingService) {
        this.audioForwardingService = audioForwardingService;
    }

    @PostMapping("/upload-audio-url")
    public ResponseEntity<String> handleAudioUrl(@RequestBody AudioRequestDto requestDto) {
        try {
            String resultText = audioForwardingService.sendAudioUrlToAi(requestDto.getAudioUrl());
            return ResponseEntity.ok(resultText);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("전송 실패: " + e.getMessage());
        }
    }
}

