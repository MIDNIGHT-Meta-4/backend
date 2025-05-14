package com.ohgiraffers.wordtoworld.controller;


import com.ohgiraffers.wordtoworld.model.dto.AudioRequestDto;
import com.ohgiraffers.wordtoworld.model.dto.QuizRequestDto;
import com.ohgiraffers.wordtoworld.model.dto.QuizResponseDto;
import com.ohgiraffers.wordtoworld.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:8080") 테스트 용
public class QuizController {
    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    QuizService quizService;
    public QuizController(QuizService quizService) {
        this.quizService = quizService;

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

    @PostMapping("/upload-audio-url")
    public ResponseEntity<Map<String, Object>> handleVoiceAnswer(
            @RequestPart("file") MultipartFile wavFile) {
        System.out.println("QuizController:handleVoiceAnswer");
        System.out.println(wavFile);

        // AI 서버에 음성 파일 전송하고 JSON 응답 받기
        Map<String, Object> resultJson = quizService.voiceconnection(wavFile);
        // JSON 응답을 그대로 반환
        return ResponseEntity.ok(resultJson);
    }

//@PostMapping("/upload-audio-url")
//public ResponseEntity<String> handleAudioUrl(@RequestParam MultipartFile requestDto) {
//    try {
//        System.out.println("handleAudioUrl");
//        System.out.println(requestDto);
//        String resultText = quizService.voiceconnection(requestDto);
//        return ResponseEntity.ok(resultText);
//    } catch (Exception e) {
//        return ResponseEntity.status(500).body("전송 실패: " + e.getMessage());
//    }
//}
}
