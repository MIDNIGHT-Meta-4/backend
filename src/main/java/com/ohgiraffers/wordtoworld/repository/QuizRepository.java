package com.ohgiraffers.wordtoworld.repository;

import com.ohgiraffers.wordtoworld.model.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
