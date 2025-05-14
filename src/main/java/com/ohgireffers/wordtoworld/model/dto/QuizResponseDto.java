package com.ohgireffers.wordtoworld.model.dto;

public class QuizResponseDto {
    private String imageUrl;       // AI가 생성한 이미지 URL
    private String description;    // AI가 생성한 이미지 설명
    private String answer;         // AI가 제공한 정답

    public QuizResponseDto() {
    }

    public QuizResponseDto(String imageUrl, String description, String answer) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.answer = answer;
    }

    // 기존 생성자 유지 (하위 호환성)
    public QuizResponseDto(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.description = description;
        this.answer = "";
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QuizResponseDto{" +
                "imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
