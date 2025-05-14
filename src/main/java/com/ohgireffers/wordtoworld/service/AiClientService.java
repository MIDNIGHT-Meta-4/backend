package com.ohgireffers.wordtoworld.service;
import com.ohgireffers.wordtoworld.model.dto.QuizRequestDto;
import com.ohgireffers.wordtoworld.model.dto.QuizResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class AiClientService {
    private final RestTemplate restTemplate;

    @Autowired
    public AiClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // AI image 서버 URL
    private static final String AI_SERVER_URL_image = "http://172.16.16.146:8000/generate/explanation";

    public QuizResponseDto requestImageFromAI(QuizRequestDto quizRequestDto) {
        try {
            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 요청 본문 데이터 생성 - 수정된 부분: toApiRequest() 메서드 사용
            Map<String, Object> requestBody = quizRequestDto.toApiRequest();

            // HTTP 요청 엔티티 생성
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // API 호출 및 응답 받기
            ResponseEntity<Map> response = restTemplate.postForEntity(AI_SERVER_URL_image, entity, Map.class);
            
            // 응답이 성공적으로 왔는지 확인
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseData = response.getBody();
                
                // AI 서버로부터 받은 이미지 URL, 설명, 정답 추출
                String imageUrl = (String) responseData.get("image_url");
                String description = (String) responseData.get("description");
                String answer = (String) responseData.get("answer");
                
                // QuizResponseDto 객체 생성 및 반환 (정답 포함)
                return new QuizResponseDto(imageUrl, description, answer);
            } else {
                // 실패 시 빈 응답 객체 반환
                return new QuizResponseDto("", "이미지 생성에 실패했습니다.", "");
            }
        } catch (Exception e) {
            // 예외 발생 시 로그 출력 및 빈 응답 객체 반환
            System.err.println("AI 서버 요청 중 오류 발생: " + e.getMessage());
            return new QuizResponseDto("", "AI 서버 연결 중 오류가 발생했습니다: " + e.getMessage(), "");
        }
    }


    public String requestVoiceFromAI(String audioUrl) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("audio_url", audioUrl);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "http://172.16.16.146:8000/voice",  // 실제 AI 서버의 음성 처리 endpoint
                    entity,
                    Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return (String) response.getBody().get("text");  // AI가 반환한 텍스트 키가 "text"라고 가정
            } else {
                return "AI 서버 응답 오류";
            }
        } catch (Exception e) {
            return "오류 발생: " + e.getMessage();
        }
    }
}
