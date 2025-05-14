package com.ohgireffers.wordtoworld.controller;


import com.ohgireffers.wordtoworld.config.RestTemplateConfig;
import com.ohgireffers.wordtoworld.model.dto.QuizRequestDto;
import com.ohgireffers.wordtoworld.model.dto.QuizResponseDto;
import com.ohgireffers.wordtoworld.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:8080") 테스트 용
public class QuizController {
    private static final Logger logger = LoggerFactory.getLogger(QuizController.class);

    QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/unity")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("sdfd");
    }


    @PostMapping("/")
    public ResponseEntity<QuizResponseDto> receiveQuiz(@ModelAttribute QuizRequestDto Quizdto) {
        System.out.println("QuizController:receiveQuiz");
        logger.info("Received Quiz Request: {}", Quizdto);
        System.out.println(Quizdto);


        List<String> words = Quizdto.getWords();
        String category = Quizdto.getCategory();

        QuizResponseDto date = quizService.processQuiz(Quizdto);
        return ResponseEntity.ok(date);
    }

    @PostMapping("/voice")
    public ResponseEntity<String> handleVoiceAnswer(
            @RequestPart("voiceFile") String voiceFileUrl) {

        // AI 서버에 음성 URL 전송하고 텍스트 받아오기
        String resultText = quizService.voiceconnection(voiceFileUrl);

        // DB에 저장할 수도 있고, 여기서는 단순 반환
        return ResponseEntity.ok(resultText);
    }
}
