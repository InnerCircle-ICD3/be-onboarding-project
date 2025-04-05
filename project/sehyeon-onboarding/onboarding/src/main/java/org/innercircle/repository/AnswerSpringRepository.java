package org.innercircle.repository;


import org.innercircle.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerSpringRepository extends JpaRepository<Answer, Long> {
}
