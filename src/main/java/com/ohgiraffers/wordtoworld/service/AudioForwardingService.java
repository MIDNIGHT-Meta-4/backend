package com.ohgiraffers.wordtoworld.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 오디오 파일을 AI 서버로 전송하여 텍스트로 변환
     * @param audioFile 클라이언트로부터 받은 오디오 파일
     * @return 변환된 텍스트 또는 오류 시 빈 문자열
     */
    public String sendAudioFileToAi(MultipartFile audioFile) {
        try {
            // 파일 유효성 검사
            if (audioFile == null || audioFile.isEmpty()) {
                logger.error("유효하지 않은 오디오 파일");
                return "";
            }
            
            // 요청 로깅
            logger.info("오디오 파일을 AI 서버로 전송: {}, 크기: {}", audioFile.getOriginalFilename(), audioFile.getSize());
            
            // 1. 요청 헤더 설정 - 멀티파트 폼으로 변경
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            
            // 2. 멀티파트 요청 바디 구성
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", audioFile.getResource());
            
            // 디버깅을 위한 요청 내용 로깅
            logger.info("AI 서버로 전송하는 요청: 파일명={}", audioFile.getOriginalFilename());
            
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            
            // 3. AI 서버에 요청 전송
            ResponseEntity<String> response = restTemplate.postForEntity(
                    AI_SERVER_URL_STT,
                    requestEntity,
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