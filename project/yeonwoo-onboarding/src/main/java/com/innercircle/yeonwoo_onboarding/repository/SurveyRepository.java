package com.innercircle.yeonwoo_onboarding.repository;

import com.innercircle.yeonwoo_onboarding.domain.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, String> {
}