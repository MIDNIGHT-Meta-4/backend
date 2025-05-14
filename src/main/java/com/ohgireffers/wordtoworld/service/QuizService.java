package com.ohgireffers.wordtoworld.service;

import com.ohgireffers.wordtoworld.model.dto.QuizRequestDto;
import com.ohgireffers.wordtoworld.model.dto.QuizResponseDto;
import com.ohgireffers.wordtoworld.model.entity.Quiz;
import com.ohgireffers.wordtoworld.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final AiClientService aiClientService;
    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(AiClientService aiClientService, QuizRepository quizRepository) {
        this.aiClientService = aiClientService;
        this.quizRepository = quizRepository;
    }

    @Transactional
    public QuizResponseDto processQuiz(QuizRequestDto quizRequestDto) {
        try {
            // AI 서버로 퀴즈 데이터를 보내고 응답 받기
            QuizResponseDto quizResponseDto = aiClientService.requestImageFromAI(quizRequestDto);

            // AI 서버 응답 검증
            if (quizResponseDto == null || quizResponseDto.getImageUrl() == null || quizResponseDto.getImageUrl().isEmpty()) {
                throw new RuntimeException("AI 서버에서 유효한 응답을 받지 못했습니다.");
            }

            // AI 서버의 응답을 DB에 저장
            saveQuizToDatabase(quizRequestDto, quizResponseDto);

            // AI 서버의 응답 반환
            return quizResponseDto;
        } catch (Exception e) {
            // 오류 로깅
            System.err.println("퀴즈 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            // 오류 발생 시 빈 응답 객체 반환
            return new QuizResponseDto("", "퀴즈 처리 중 오류가 발생했습니다: " + e.getMessage(), "");
        }
    }

    /**
     * AI 응답 데이터를 DB에 저장하는 메소드
     */

    private void saveQuizToDatabase(QuizRequestDto request, QuizResponseDto response) {
        try {
            // 키워드들을 쉼표로 구분된 문자열로 변환
            String words = String.join(",", request.getWords());

            // 설명 필드 길이 검증 (옵션)
            String description = response.getDescription();
            if (description != null && description.length() > 65535) { // TEXT 타입 최대 길이
                description = description.substring(0, 65535);
                System.out.println("경고: 설명 필드가 너무 길어 잘렸습니다.");
            }

            // Quiz 엔티티 생성
            Quiz quiz = new Quiz(
                    request.getCategory(),        // 카테고리
                    response.getImageUrl(),       // 이미지 URL
                    description,                  // 설명 (길이 검증됨)
                    response.getAnswer(),         // 정답
                    words                        // 키워드들
            );
            
            // 현재 시간 설정
            quiz.setUploadedAt(LocalDateTime.now());
            
            // DB에 저장
            Quiz savedQuiz = quizRepository.save(quiz);
            
            System.out.println("Quiz 저장 완료: " + savedQuiz);
        } catch (Exception e) {
            // 저장 실패 시 로그 기록
            System.err.println("Quiz 저장 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            // 예외를 상위로 전파하여 트랜잭션이 롤백되도록 함
            throw new RuntimeException("Quiz를 데이터베이스에 저장하는 데 실패했습니다.", e);
        }
    }

    @Transactional
    public String voiceconnection(String voiceFileUrl) {
        return aiClientService.requestVoiceFromAI(voiceFileUrl);
    }
}
