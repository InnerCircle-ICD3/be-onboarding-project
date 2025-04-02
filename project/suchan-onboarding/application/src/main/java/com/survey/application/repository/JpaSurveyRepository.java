package com.survey.application.repository;

import com.survey.domain.ChoiceInputForm;
import com.survey.domain.Survey;
import com.survey.domain.SurveyOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaSurveyRepository extends JpaRepository<Survey, Long> {

    @Query("SELECT s FROM Survey s " +
            "LEFT JOIN FETCH s.surveyOptions " +
            "WHERE s.id = :id")
    Optional<Survey> findByIdWithSurveyOptions(@Param("id") Long id);

    @Query("SELECT so FROM SurveyOption so " +
            "LEFT JOIN FETCH so.inputForm if " +
            "LEFT JOIN FETCH if.textInputForm " +
            "LEFT JOIN FETCH if.choiceInputForm ci " +
            "WHERE so.survey.id = :surveyId")
    List<SurveyOption> findSurveyOptionsWithInputFormsBySurveyId(@Param("surveyId") Long surveyId);

    @Query("SELECT ci FROM ChoiceInputForm ci " +
            "LEFT JOIN FETCH ci.inputOptions " +
            "WHERE ci.inputForm.surveyOption.survey.id = :surveyId")
    List<ChoiceInputForm> findChoiceInputFormWithOptionsBySurveyId(@Param("surveyId") Long surveyId);
}
