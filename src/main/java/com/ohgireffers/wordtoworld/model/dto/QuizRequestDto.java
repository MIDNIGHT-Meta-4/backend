package com.ohgireffers.wordtoworld.model.dto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizRequestDto {

    private String KeyWord1;
    private String KeyWord2;
    private String KeyWord3;
    private String Category;




    public List<String> getWords() {
        return List.of(KeyWord1, KeyWord2, KeyWord3);
    }

    public QuizRequestDto(String keyWord1, String keyWord2, String keyWord3, String category) {
        KeyWord1 = keyWord1;
        KeyWord2 = keyWord2;
        KeyWord3 = keyWord3;
        Category = category;
    }

    public String getKeyWord1() {
        return KeyWord1;
    }

    public void setKeyWord1(String keyWord1) {
        KeyWord1 = keyWord1;
    }

    public String getKeyWord2() {
        return KeyWord2;
    }

    public void setKeyWord2(String keyWord2) {
        KeyWord2 = keyWord2;
    }

    public String getKeyWord3() {
        return KeyWord3;
    }

    public void setKeyWord3(String keyWord3) {
        KeyWord3 = keyWord3;
    }

    public String getCategory() {
        return Category;
    }

    @Override
    public String toString() {
        return "QuizRequestDto{" +
                "KeyWord1='" + KeyWord1 + '\'' +
                ", KeyWord2='" + KeyWord2 + '\'' +
                ", KeyWord3='" + KeyWord3 + '\'' +
                ", Category='" + Category + '\'' +
                '}';
    }

    public void setCategory(String category) {
        Category = category;
    }

    public Map<String, Object> toApiRequest() {
        Map<String, Object> request = new HashMap<>();
        List<String> keywords = Arrays.asList(KeyWord1, KeyWord2, KeyWord3);
        request.put("keywords", keywords);
        request.put("category", getCategory());
        return request;
    } // 키워드 목록을 리스트로 변경
}
