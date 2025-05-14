package com.ohgiraffers.wordtoworld.controller;


import com.ohgiraffers.wordtoworld.model.dto.AudioRequestDto;
import com.ohgiraffers.wordtoworld.model.dto.QuizRequestDto;
import com.ohgiraffers.wordtoworld.model.dto.QuizResponseDto;
import com.ohgiraffers.wordtoworld.service.AudioForwardingService;
import com.ohgiraffers.wordtoworld.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:8080") 테스트 용
public class QuizController {
    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    QuizService quizService;
    AudioForwardingService audioForwardingService;

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

//    @PostMapping("/voice")
//    public ResponseEntity<MultipartFile> handleVoiceAnswer(
//            @RequestPart("voiceFile") MultipartFile voiceFileUrl) {
//
//        // AI 서버에 음성 URL 전송하고 텍스트 받아오기
//        MultipartFile resultText = quizService.voiceconnection(voiceFileUrl);
//
//        // DB에 저장할 수도 있고, 여기서는 단순 반환
//        return ResponseEntity.ok(resultText);
//    }

@PostMapping("/upload-audio-url")
public ResponseEntity<String> handleAudioUrl(@RequestBody AudioRequestDto requestDto) {
    try {
        System.out.println("handleAudioUrl");
        System.out.println(requestDto);
        String resultText = audioForwardingService.sendAudioUrlToAi(requestDto.getAudioUrl());
        return ResponseEntity.ok(resultText);
    } catch (Exception e) {
        return ResponseEntity.status(500).body("전송 실패: " + e.getMessage());
    }
}
}
