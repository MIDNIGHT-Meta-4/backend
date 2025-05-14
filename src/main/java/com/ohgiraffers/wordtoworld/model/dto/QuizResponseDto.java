package com.ohgiraffers.wordtoworld.model.dto;

/**
 * AI 서버로부터 받은 퀴즈 생성 결과를 담는 DTO 클래스
 * 생성된 이미지 URL, 설명, 정답 정보를 포함합니다.
 */
public class QuizResponseDto {
    // 생성된 이미지의 URL
    private String imageUrl;
    
    // 이미지에 대한 설명 텍스트
    private String description;
    
    // 퀴즈의 정답
    private String answer;

    /**
     * 기본 생성자
     */
    public QuizResponseDto() {
    }

    /**
     * 모든 필드를 초기화하는 생성자
     * @param imageUrl 생성된 이미지 URL
     * @param description 이미지 설명
     * @param answer 퀴즈 정답
     */
    public QuizResponseDto(String imageUrl, String description, String answer) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.answer = answer;
    }

    /**
     * 이전 버전과의 호환성을 위한 생성자 (정답 없음)
     * 정답은 빈 문자열로 초기화됩니다.
     * 
     * @param imageUrl 생성된 이미지 URL
     * @param description 이미지 설명
     */
    public QuizResponseDto(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.answer = "";
    }

    /**
     * 이미지 URL 반환
     * @return 생성된 이미지 URL
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 이미지 URL 설정
     * @param imageUrl 설정할 이미지 URL
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 이미지 설명 반환
     * @return 이미지에 대한 설명 텍스트
     */
    public String getDescription() {
        return description;
    }

    /**
     * 이미지 설명 설정
     * @param description 설정할 설명 텍스트
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 퀴즈 정답 반환
     * @return 퀴즈의 정답
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 퀴즈 정답 설정
     * @param answer 설정할 정답
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * DTO 객체를 문자열로 표현
     * @return 객체의 내용을 담은 문자열
     */
    @Override
    public String toString() {
        return "QuizResponseDto{" +
                "imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
