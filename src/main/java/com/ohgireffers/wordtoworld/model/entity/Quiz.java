package com.ohgireffers.wordtoworld.model.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 생성된 ID
    private Long id;

    @Column(nullable = true, length = 100)  // 카테고리는 필수 항목
    private String category;

    @Column(nullable = false)  // 이미지 URL 필수
    private String imageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")  // 설명 필수, TEXT 타입으로 변경
    private String description;

    @Column(nullable = false)  // 정답 필수
    private String answer;

    @Column(nullable = true)  // 단어들, 쉼표로 구분된 문자열로 저장
    private String words;


    @Column(nullable = false, updatable = false)  // 업로드 시간
    private LocalDateTime uploadedAt;


    public Quiz() {

    }

    // 생성자 (기본 생성자와 필요한 생성자 추가 가능)
    public Quiz(String category, String imageUrl, String description, String answer, String words) {
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
        this.answer = answer;
        this.words = words;

        this.uploadedAt = LocalDateTime.now();  // 업로드 시간은 현재 시간으로 설정
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }



    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }


    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", answer='" + answer + '\'' +
                ", words='" + words + '\'' +
                ", uploadedAt=" + uploadedAt ;
    }
}
