package com.survey.application.repository;

import com.survey.domain.surveyResponse.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaSurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {

    @Query("SELECT s FROM SurveyResponse s " +
            "LEFT JOIN fetch s.surveyOptionResponses so " +
            "WHERE s.surveyId = :surveyId")
    List<SurveyResponse> findBySurveyIdFetchJoin(@Param("surveyId") Long surveyId);
}
