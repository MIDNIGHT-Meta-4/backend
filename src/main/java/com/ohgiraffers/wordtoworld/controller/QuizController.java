package com.ohgiraffers.wordtoworld.controller;

import com.ohgiraffers.wordtoworld.model.dto.AudioRequestDto;
import com.ohgiraffers.wordtoworld.model.dto.QuizRequestDto;
import com.ohgiraffers.wordtoworld.model.dto.QuizResponseDto;
import com.ohgiraffers.wordtoworld.service.AudioForwardingService;
import com.ohgiraffers.wordtoworld.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:8080") 테스트 용
public class QuizController {
    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    private final QuizService quizService;
    private final AudioForwardingService audioForwardingService;

    @Autowired
    public QuizController(QuizService quizService, AudioForwardingService audioForwardingService) {
        this.quizService = quizService;
        this.audioForwardingService = audioForwardingService;
    }

    @PostMapping("/image")
    public ResponseEntity<QuizResponseDto> receiveQuiz(@ModelAttribute QuizRequestDto Quizdto) {
        System.out.println("QuizController:receiveQuiz");
        logger.info("Received Quiz Request: {}", Quizdto);
        System.out.println(Quizdto);

        List<String> words = Quizdto.getWords();
        String category = Quizdto.getCategory();

        QuizResponseDto date = quizService.processQuiz(Quizdto);
        return ResponseEntity.ok(date);
    }

    @PostMapping("/stt")
    public ResponseEntity<String> handleSpeechToText(@RequestBody Map<String, String> requestMap) {
        try {
            // 로깅을 통한 요청 확인
            logger.info("STT 요청 받음: {}", requestMap);
            
            // API 명세서에 따라 audio_url 파라미터를 사용
            String audioUrl = requestMap.get("audio_url");
            
            if (audioUrl == null || audioUrl.trim().isEmpty()) {
                logger.error("유효하지 않은 오디오 URL: {}", audioUrl);
                return ResponseEntity.ok(""); // 빈 문자열 반환
            }
            
            // AI 서버에 URL 전송하고 변환된 텍스트 받기
            String resultText = audioForwardingService.sendAudioUrlToAi(audioUrl);
            
            // 결과 로깅
            logger.info("STT 변환 결과: {}", resultText);
            
            // 결과 반환
            return ResponseEntity.ok(resultText);
        } catch (Exception e) {
            logger.error("STT 처리 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.ok(""); // 빈 문자열 반환
        }
    }
}