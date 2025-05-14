package com.ohgiraffers.wordtoworld.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(name = "image_url", columnDefinition = "TEXT", nullable = false)
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String answer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String words;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "audio_url", columnDefinition = "TEXT")
    private String audioUrl;

    @Column(name = "audio_transcript")
    private String audioTranscript;

    @Column(name = "uploaded_at", insertable = false, updatable = false)
    private LocalDateTime uploadedAt;

    public Quiz() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getAudioTranscript() {
        return audioTranscript;
    }

    public void setAudioTranscript(String audioTranscript) {
        this.audioTranscript = audioTranscript;
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
                ", createdAt=" + createdAt +
                ", audioUrl='" + audioUrl + '\'' +
                ", audioFilename='" + audioTranscript + '\'' +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}
