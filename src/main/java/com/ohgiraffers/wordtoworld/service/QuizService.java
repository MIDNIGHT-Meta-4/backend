package com.ohgiraffers.wordtoworld.service;

import com.ohgiraffers.wordtoworld.model.dto.QuizRequestDto;
import com.ohgiraffers.wordtoworld.model.dto.QuizResponseDto;
import com.ohgiraffers.wordtoworld.model.entity.Quiz;
import com.ohgiraffers.wordtoworld.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
            System.out.println("quizResponseDto = " + quizResponseDto);
            
            // AI 서버 응답 검증 - null 체크만 하고 빈 문자열은 허용
            if (quizResponseDto == null) {
                // null인 경우 빈 응답 객체 반환 (모든 필드가 빈 문자열)
                return new QuizResponseDto("", "", "");
            }
            
            // 응답 필드가 null인 경우 빈 문자열로 변환
            if (quizResponseDto.getImageUrl() == null) quizResponseDto.setImageUrl("");
            if (quizResponseDto.getDescription() == null) quizResponseDto.setDescription("");
            if (quizResponseDto.getAnswer() == null) quizResponseDto.setAnswer("");

            // AI 서버의 응답을 DB에 저장 - 이미지 URL이 비어있어도 저장 시도
            try {
                saveQuizToDatabase(quizRequestDto, quizResponseDto);
            } catch (Exception e) {
                // DB 저장 실패는 로깅만 하고 계속 진행
                System.err.println("DB 저장 중 오류가 발생했지만, 응답은 계속 진행합니다: " + e.getMessage());
            }

            // AI 서버의 응답 반환
            return quizResponseDto;
        } catch (Exception e) {
            // 오류 로깅
            System.err.println("퀴즈 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            // 오류 발생 시 빈 응답 객체 반환 (모든 필드가 빈 문자열)
            return new QuizResponseDto("", "", "");
        }
    }

    /**
     * AI 응답 데이터를 DB에 저장하는 메소드
     */

    private void saveQuizToDatabase(QuizRequestDto request, QuizResponseDto response) {
        try {
            // null 체크 및 빈 문자열 처리
            String category = (request.getCategory() != null) ? request.getCategory() : "";
            String imageUrl = (response.getImageUrl() != null) ? response.getImageUrl() : "";
            String description = (response.getDescription() != null) ? response.getDescription() : "";
            String answer = (response.getAnswer() != null) ? response.getAnswer() : "";

            // 키워드들을 쉼표로 구분된 문자열로 변환 (null 체크 포함)
            String words = "";
            if (request.getWords() != null) {
                // null이 아닌 키워드만 포함
                words = request.getWords().stream()
                        .filter(word -> word != null)
                        .map(word -> word.trim())
                        .filter(word -> !word.isEmpty())
                        .reduce((a, b) -> a + "," + b)
                        .orElse("");
            }

            // 설명 필드 길이 검증 (옵션)
            if (description.length() > 65535) { // TEXT 타입 최대 길이
                description = description.substring(0, 65535);
                System.out.println("경고: 설명 필드가 너무 길어 잘렸습니다.");
            }

            // Quiz 엔티티 생성
            Quiz quiz = new Quiz(
                    category,        // 카테고리
                    imageUrl,        // 이미지 URL
                    description,     // 설명 (길이 검증됨)
                    answer,          // 정답
                    words            // 키워드들
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

//    @Transactional
//    public String voiceconnection(String voiceFileUrl) {
//        return aiClientService.requestVoiceFromAI(voiceFileUrl);
//    }

}
