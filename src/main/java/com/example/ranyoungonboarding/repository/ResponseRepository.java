package com.example.ranyoungonboarding.repository;

import com.example.ranyoungonboarding.domain.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    // 설문조사별 응답 목록 조회 (페이징 적용)
    Page<Response> findBySurveyId(Long surveyId, Pageable pageable);

    // 고급 검색 기능을 위한 쿼리 (특정 질문에 대한 특정 답변을 포함하는 응답 검색)
    @Query("SELECT DISTINCT r FROM Response r JOIN r.answers a WHERE r.survey.id = :surveyId " +
            "AND a.question.name LIKE %:questionName% AND " +
            "(a.textValue LIKE %:answerValue% OR EXISTS (SELECT 1 FROM a.multipleValues mv WHERE mv LIKE %:answerValue%))")
    Page<Response> searchByQuestionAndAnswer(
            @Param("surveyId") Long surveyId,
            @Param("questionName") String questionName,
            @Param("answerValue") String answerValue,
            Pageable pageable);
}