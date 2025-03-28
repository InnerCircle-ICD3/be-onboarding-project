package com.survey.application.repository;

import com.survey.domain.InputForm;
import com.survey.domain.SurveyOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaInputFormRepository extends JpaRepository<InputForm, Long> {

    @Query("SELECT if FROM InputForm if " +
            "LEFT JOIN FETCH if.textInputForm " +
            "LEFT JOIN FETCH if.choiceInputForm ci " +
            "WHERE if.surveyOption IN :surveyOptions")
    List<InputForm> findBySurveysOptionFetchJoin(@Param("surveyOptions") List<SurveyOption> surveyOptions);
}
