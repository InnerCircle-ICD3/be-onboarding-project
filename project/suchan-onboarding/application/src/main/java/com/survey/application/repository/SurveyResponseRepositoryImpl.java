package com.survey.application.repository;

import com.survey.domain.surveyResponse.SurveyResponse;
import com.survey.domain.surveyResponse.repository.SurveyResponseRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SurveyResponseRepositoryImpl implements SurveyResponseRepository {

    private final JpaSurveyResponseRepository jpaSurveyResponseRepository;

    public SurveyResponseRepositoryImpl(JpaSurveyResponseRepository jpaSurveyResponseRepository) {
        this.jpaSurveyResponseRepository = jpaSurveyResponseRepository;
    }

    @Override
    public void save(SurveyResponse surveyResponse) {
        jpaSurveyResponseRepository.save(surveyResponse);
    }
}
