package com.ohgiraffers.wordtoworld.service;
import com.ohgiraffers.wordtoworld.model.dto.AudioResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AudioForwardingService {
    private static final Logger logger = LoggerFactory.getLogger(AudioForwardingService.class);
    
    private final RestTemplate restTemplate;
    
    // AI 서비스 STT URL 정의
    private static final String AI_SERVER_URL_STT = "http://172.16.16.146:8000/stt";
    
    @Autowired
    public AudioForwardingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendAudioUrlToAi(String audioUrl) {
        try {
            // 요청 로깅
            logger.info("음성 URL을 AI 서버로 전송: {}", audioUrl);
            
            // 1. 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 2. JSON 요청 바디 구성 - API 명세서에 맞게 audio_url로 키 이름 변경
            Map<String, String> bodyMap = new HashMap<>();
            bodyMap.put("audio_url", audioUrl);
            
            // 디버깅을 위한 요청 내용 로깅
            logger.info("AI 서버로 전송하는 요청 본문: {}", bodyMap);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(bodyMap, headers);

            // 3. AI 서버에 요청 전송
            // 응답 타입을 String으로 변경 (API 명세서에서는 단순 문자열 반환)
            ResponseEntity<String> response = restTemplate.postForEntity(
                    AI_SERVER_URL_STT,
                    request,
                    String.class
            );

            // 응답 로깅
            logger.info("AI 서버 응답 상태: {}", response.getStatusCode());
            logger.info("AI 서버 응답 본문: {}", response.getBody());

            // 4. 결과 텍스트 반환
            if (response.getStatusCode().is2xxSuccessful()) {
                // null이면 빈 문자열로 처리
                return (response.getBody() != null) ? response.getBody() : "";
            } else {
                logger.error("AI 서버에서 성공 응답을 받지 못했습니다. 상태: {}", response.getStatusCode());
                return ""; // 오류 시에도 빈 문자열 반환
            }
        } catch (Exception e) {
            // 예외 처리 및 로깅
            logger.error("음성 인식 중 오류 발생: {}", e.getMessage(), e);
            return ""; // 오류 시 빈 문자열 반환
        }
    }
}