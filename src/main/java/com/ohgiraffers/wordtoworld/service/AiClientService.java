package com.ohgiraffers.wordtoworld.service;
import com.ohgiraffers.wordtoworld.model.dto.QuizRequestDto;
import com.ohgiraffers.wordtoworld.model.dto.QuizResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * AI 서버와의 통신을 담당하는 서비스 클래스
 * 이미지 생성과 음성 텍스트 변환 요청을 처리합니다.
 */
@Service
public class AiClientService {
    // 로깅을 위한 Logger 객체 생성
    private static final Logger logger = LoggerFactory.getLogger(AiClientService.class);
    
    // HTTP 요청을 위한 RestTemplate
    private final RestTemplate restTemplate;

    /**
     * 생성자 주입을 통한 RestTemplate 초기화
     * @param restTemplate HTTP 요청 처리를 위한 Spring RestTemplate
     */
    @Autowired
    public AiClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // AI 서비스 요청 URL 정의
    // 이미지 생성 서비스 URL
    private static final String AI_SERVER_URL_image = "http://172.16.16.146:8000/generate";
    // 음성-텍스트 변환 서비스 URL (STT: Speech-to-Text)
    private static final String AI_SERVER_URL_voice = "http://172.16.16.146:8000/stt";

    /**
     * AI 서버에 퀴즈 이미지 생성 요청을 보내는 메서드
     * @param quizRequestDto 키워드와 카테고리 정보를 담은 DTO
     * @return 생성된 이미지 URL, 설명, 정답을 담은 DTO
     */
    public QuizResponseDto requestImageFromAI(QuizRequestDto quizRequestDto) {
        try {
            // HTTP 헤더 설정 (JSON 형식으로 요청)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 요청 본문 데이터 생성 - 수정된 부분: toApiRequest() 메서드 사용
            // 이 메서드는 키워드 배열과 카테고리를 포함한 요청 맵을 생성
            Map<String, Object> requestBody = quizRequestDto.toApiRequest();

            // HTTP 요청 엔티티 생성 (헤더와 본문 포함)
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // API 호출 및 응답 받기
            ResponseEntity<Map> response = restTemplate.postForEntity(AI_SERVER_URL_image, entity, Map.class);
            
            // 응답이 성공적으로 왔는지 확인 (2xx 상태 코드)
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseData = response.getBody();
                
                // AI 서버로부터 받은 이미지 URL, 설명, 정답 추출
                String imageUrl = (String) responseData.get("image_url");
                String explanation = (String) responseData.get("explanation");
                String answer = (String) responseData.get("answer");
                
                // QuizResponseDto 객체 생성 및 반환 (정답 포함)
                return new QuizResponseDto(imageUrl, explanation, answer);
            } else {
                // 실패 시 빈 응답 객체 반환
                return new QuizResponseDto("", "이미지 생성에 실패했습니다.", "");
            }
        } catch (Exception e) {
            // 예외 발생 시 로그 출력 및 빈 응답 객체 반환
            logger.error("AI 서버 요청 중 오류 발생: {}", e.getMessage());
            return new QuizResponseDto("", "AI 서버 연결 중 오류가 발생했습니다: " + e.getMessage(), "");
        }
    }


    public String requestVoiceFromAI(MultipartFile audioFile) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", audioFile.getResource());

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    AI_SERVER_URL_voice,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                return "음성 인식 실패";
            }
        } catch (Exception e) {
            return "오류 발생: " + e.getMessage();
        }
    }
    /**
     * URL 형식이 유효한지 검사하는 도우미 메서드
     * @param url 검사할 URL 문자열
     * @return URL이 유효하면 true, 그렇지 않으면 false
     */
    private boolean isValidUrl(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }
        
        try {
            // URL 클래스의 생성자를 통해 유효성 검사
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            // URL 형식이 잘못된 경우
            return false;
        }
    }
}
