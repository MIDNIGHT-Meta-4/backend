package com.ohgiraffers.wordtoworld.service;
import com.ohgiraffers.wordtoworld.model.dto.AudioResponseDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AudioForwardingService {

    public String sendAudioUrlToAi(String audioUrl) {
        // 1. 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 2. JSON 요청 바디 구성
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("audio_url", audioUrl); // AI 서버가 요구하는 key 확인 필요

        HttpEntity<Map<String, String>> request = new HttpEntity<>(bodyMap, headers);

        // 3. AI 서버에 요청 전송
        RestTemplate restTemplate = new RestTemplate();
        String aiServerUrl = "http://172.16.16.146:8000/stt";

        ResponseEntity<AudioResponseDto> response = restTemplate.postForEntity(
                aiServerUrl,
                request,
                AudioResponseDto.class
        );

        // 4. 결과 텍스트 반환

        return response.getBody().getResult_text();
    }
}
