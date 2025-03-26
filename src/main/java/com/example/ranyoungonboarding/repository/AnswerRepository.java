package com.example.ranyoungonboarding.repository;

import com.example.ranyoungonboarding.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByResponseId(Long responseId);
    List<Answer> findByQuestionId(Long questionId);
}