package com.survey.application.repository;

import com.survey.domain.Survey;
import com.survey.domain.repository.SurveyRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SurveyRepositoryImpl implements SurveyRepository {

    private final JpaSurveyRepository jpaSurveyRepository;

    public SurveyRepositoryImpl(JpaSurveyRepository jpaSurveyRepository) {
        this.jpaSurveyRepository = jpaSurveyRepository;
    }

    @Override
    public Survey save(Survey survey) {
        return jpaSurveyRepository.save(survey);
    }
}
