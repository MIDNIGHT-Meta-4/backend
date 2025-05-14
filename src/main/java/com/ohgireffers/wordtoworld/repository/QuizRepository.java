package com.ohgireffers.wordtoworld.repository;

import com.ohgireffers.wordtoworld.model.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
