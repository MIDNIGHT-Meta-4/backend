package com.ohgiraffers.wordtoworld.model.dto;

import java.util.*;

/**
 * 유니티 클라이언트로부터 받은 퀴즈 생성 요청 정보를 담는 DTO 클래스
 * 키워드와 카테고리 정보를 포함합니다.
 */
public class QuizRequestDto {

    // 키워드들 (최대 3개)
    private String KeyWord1;
    private String KeyWord2;
    private String KeyWord3;
    
    // 카테고리 정보
    private String Category;

    /**
     * 키워드 목록을 리스트 형태로 반환
     * @return 키워드 1, 2, 3을 포함한 List
     */
    public List<String> getWords() {
        return List.of(KeyWord1, KeyWord2, KeyWord3);
    }

    /**
     * 모든 필드를 초기화하는 생성자
     * @param keyWord1 첫 번째 키워드
     * @param keyWord2 두 번째 키워드
     * @param keyWord3 세 번째 키워드
     * @param category 카테고리
     */
    public QuizRequestDto(String keyWord1, String keyWord2, String keyWord3, String category) {
        KeyWord1 = keyWord1;
        KeyWord2 = keyWord2;
        KeyWord3 = keyWord3;
        Category = category;
    }

    // Getter와 Setter 메서드

    /**
     * 첫 번째 키워드 반환
     * @return 첫 번째 키워드
     */
    public String getKeyWord1() {
        return KeyWord1;
    }

    /**
     * 첫 번째 키워드 설정
     * @param keyWord1 설정할 키워드
     */
    public void setKeyWord1(String keyWord1) {
        KeyWord1 = keyWord1;
    }

    /**
     * 두 번째 키워드 반환
     * @return 두 번째 키워드
     */
    public String getKeyWord2() {
        return KeyWord2;
    }

    /**
     * 두 번째 키워드 설정
     * @param keyWord2 설정할 키워드
     */
    public void setKeyWord2(String keyWord2) {
        KeyWord2 = keyWord2;
    }

    /**
     * 세 번째 키워드 반환
     * @return 세 번째 키워드
     */
    public String getKeyWord3() {
        return KeyWord3;
    }

    /**
     * 세 번째 키워드 설정
     * @param keyWord3 설정할 키워드
     */
    public void setKeyWord3(String keyWord3) {
        KeyWord3 = keyWord3;
    }

    /**
     * 카테고리 반환
     * @return 카테고리
     */
    public String getCategory() {
        return Category;
    }

    /**
     * 카테고리 설정
     * @param category 설정할 카테고리
     */
    public void setCategory(String category) {
        Category = category;
    }

    /**
     * DTO 객체를 문자열로 표현
     * @return 객체의 내용을 담은 문자열
     */
    @Override
    public String toString() {
        return "QuizRequestDto{" +
                "KeyWord1='" + KeyWord1 + '\'' +
                ", KeyWord2='" + KeyWord2 + '\'' +
                ", KeyWord3='" + KeyWord3 + '\'' +
                ", Category='" + Category + '\'' +
                '}';
    }

    /**
     * AI 서버에 전송할 요청 형식으로 변환하는 메서드
     * - keywords 배열: 유효한(null이 아니고 비어있지 않은) 키워드만 포함
     * - category: 카테고리 정보
     * 
     * @return AI 서버 요청에 맞는 Map 형태의 데이터
     */
    public Map<String, Object> toApiRequest() {
        Map<String, Object> request = new HashMap<>();

        // null이 아닌 키워드만 리스트에 추가
        List<String> keywordsList = new ArrayList<>();
        if (KeyWord1 != null && !KeyWord1.trim().isEmpty()) {
            keywordsList.add(KeyWord1);
        }
        if (KeyWord2 != null && !KeyWord2.trim().isEmpty()) {
            keywordsList.add(KeyWord2);
        }
        if (KeyWord3 != null && !KeyWord3.trim().isEmpty()) {
            keywordsList.add(KeyWord3);
        }

        // API 요청 형식에 맞게 데이터 추가
        request.put("keywords", keywordsList);
        request.put("category", getCategory());
        return request;
    }
}
