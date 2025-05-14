package com.ohgiraffers.wordtoworld.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * 퀴즈 정보를 저장하는 엔티티 클래스
 * 데이터베이스의 Quiz 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "Quiz")
public class Quiz {
    /**
     * 퀴즈 ID (자동 생성)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 퀴즈 카테고리 (최대 100자)
     */
    @Column(nullable = true, length = 100)
    private String category;

    /**
     * 생성된 이미지 URL
     */
    @Column(nullable = false)
    private String imageUrl;

    /**
     * 이미지에 대한 설명 (TEXT 타입으로 큰 텍스트 저장 가능)
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    /**
     * 퀴즈 정답
     */
    @Column(nullable = false)
    private String answer;

    /**
     * 퀴즈 생성에 사용된 키워드들 (쉼표로 구분된 문자열)
     */
    @Column(nullable = true)
    private String words;

    /**
     * 퀴즈 생성 시간 (자동 설정, 변경 불가)
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadedAt;

    /**
     * 기본 생성자
     */
    public Quiz() {
    }

    /**
     * 퀴즈 정보를 초기화하는 생성자
     *
     * @param category 카테고리
     * @param imageUrl 이미지 URL
     * @param description 설명
     * @param answer 정답
     * @param words 키워드 문자열 (쉼표로 구분)
     */
    public Quiz(String category, String imageUrl, String description, String answer, String words) {
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
        this.answer = answer;
        this.words = words;
        this.uploadedAt = LocalDateTime.now();  // 업로드 시간은 현재 시간으로 설정
    }

    // Getter와 Setter 메서드

    /**
     * 퀴즈 ID 반환
     * @return 퀴즈 ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 퀴즈 ID 설정
     * @param id 설정할 ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 카테고리 반환
     * @return 퀴즈 카테고리
     */
    public String getCategory() {
        return category;
    }

    /**
     * 카테고리 설정
     * @param category 설정할 카테고리
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 이미지 URL 반환
     * @return 이미지 URL
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
     * 설명 반환
     * @return 이미지 설명 텍스트
     */
    public String getDescription() {
        return description;
    }

    /**
     * 설명 설정
     * @param description 설정할 설명 텍스트
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 정답 반환
     * @return 퀴즈 정답
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 정답 설정
     * @param answer 설정할 정답
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 키워드 문자열 반환
     * @return 쉼표로 구분된 키워드 문자열
     */
    public String getWords() {
        return words;
    }

    /**
     * 키워드 문자열 설정
     * @param words 설정할 키워드 문자열
     */
    public void setWords(String words) {
        this.words = words;
    }

    /**
     * 업로드 시간 반환
     * @return 퀴즈 생성 시간
     */
    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    /**
     * 업로드 시간 설정
     * @param uploadedAt 설정할 업로드 시간
     */
    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    /**
     * 엔티티 객체를 문자열로 표현
     * @return 객체의 내용을 담은 문자열
     */
    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", answer='" + answer + '\'' +
                ", words='" + words + '\'' +
                ", uploadedAt=" + uploadedAt;
    }
}
