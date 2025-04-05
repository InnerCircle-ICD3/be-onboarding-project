package com.survey.application.repository;

import com.survey.domain.survey.ChoiceInputForm;
import com.survey.domain.survey.InputForm;
import com.survey.domain.survey.Survey;
import com.survey.domain.survey.SurveyOption;
import com.survey.domain.survey.repository.SurveyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SurveyRepositoryImpl implements SurveyRepository {

    private final JpaSurveyRepository jpaSurveyRepository;
    private final JpaSurveyOptionRepository jpaSurveyOptionRepository;
    private final JpaInputFormRepository jpaInputFormRepository;
    private final JpaChoiceInputFormRepository jpaChoiceInputFormRepository;

    public SurveyRepositoryImpl(JpaSurveyRepository jpaSurveyRepository,
                                JpaSurveyOptionRepository jpaSurveyOptionRepository,
                                JpaInputFormRepository jpaInputFormRepository, JpaChoiceInputFormRepository jpaChoiceInputFormRepository) {
        this.jpaSurveyRepository = jpaSurveyRepository;
        this.jpaSurveyOptionRepository = jpaSurveyOptionRepository;
        this.jpaInputFormRepository = jpaInputFormRepository;
        this.jpaChoiceInputFormRepository = jpaChoiceInputFormRepository;
    }

    @Override
    public Survey save(Survey survey) {
        return jpaSurveyRepository.save(survey);
    }

    @Override
    public Optional<Survey> findById(Long surveyId) {
        return jpaSurveyRepository.findById(surveyId);
    }

    @Override
    public Optional<Survey> findCompleteSurvey(Long surveyId) {
        Optional<Survey> optionalSurvey = jpaSurveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            List<SurveyOption> surveyOptions = jpaSurveyOptionRepository.findBySurvey(optionalSurvey.get());
            List<InputForm> inputForms = jpaInputFormRepository.findBySurveysOptionFetchJoin(surveyOptions);
            List<ChoiceInputForm> choiceInputForms = jpaChoiceInputFormRepository.findByInputFormsFetchJoin(inputForms);
        }
        return optionalSurvey;
    }

}
