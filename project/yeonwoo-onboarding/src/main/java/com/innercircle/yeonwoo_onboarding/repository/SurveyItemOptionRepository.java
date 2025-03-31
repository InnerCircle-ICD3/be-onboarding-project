package com.innercircle.yeonwoo_onboarding.repository;

import com.innercircle.yeonwoo_onboarding.domain.SurveyItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurveyItemOptionRepository extends JpaRepository<SurveyItemOption, String> {
    List<SurveyItemOption> findBySurveyItemId(String surveyItemId);
}