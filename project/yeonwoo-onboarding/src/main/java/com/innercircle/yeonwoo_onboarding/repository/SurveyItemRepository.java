package com.innercircle.yeonwoo_onboarding.repository;

import com.innercircle.yeonwoo_onboarding.domain.SurveyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurveyItemRepository extends JpaRepository<SurveyItem, String> {
    List<SurveyItem> findBySurveyId(String surveyId);
}