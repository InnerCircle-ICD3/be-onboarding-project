package com.innercircle.yeonwoo_onboarding.repository;

import com.innercircle.yeonwoo_onboarding.domain.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, String> {
    List<SurveyResponse> findBySurveyId(String surveyId);
}