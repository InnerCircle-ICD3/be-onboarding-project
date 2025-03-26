package com.survey.application.repository;

import com.survey.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaSurveyRepository extends JpaRepository<Survey, Long> {

    @Query("SELECT s FROM Survey s " +
            "LEFT JOIN FETCH s.surveyOptions so " +
            "LEFT JOIN FETCH so.inputForm if " +
            "LEFT JOIN FETCH if.textInputForm " +
            "LEFT JOIN FETCH if.choiceInputForm ci " +
            "LEFT JOIN FETCH ci.inputOptions " +
            "WHERE s.id = :id")
    Optional<Survey> findCompleteSurveyFetchJoin(@Param("id") Long id);
}
