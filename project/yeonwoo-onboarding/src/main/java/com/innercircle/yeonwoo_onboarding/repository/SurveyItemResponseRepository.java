package com.innercircle.yeonwoo_onboarding.repository;

import com.innercircle.yeonwoo_onboarding.domain.SurveyItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurveyItemResponseRepository extends JpaRepository<SurveyItemResponse, String> {
    List<SurveyItemResponse> findBySurveyResponseId(String surveyResponseId);
}