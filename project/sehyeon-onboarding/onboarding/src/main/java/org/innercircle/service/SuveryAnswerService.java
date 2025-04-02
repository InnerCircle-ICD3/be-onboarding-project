package org.innercircle.service;

import org.innercircle.entity.SurveyAnswer;
import org.innercircle.repository.SurveyAnswerSpringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SuveryAnswerService {

    @Autowired
    SurveyAnswerSpringRepository surveyAnswerRepository;

    public Long saveSurveyAnswer(SurveyAnswer surveyAnswer) {
        surveyAnswerRepository.save(surveyAnswer);
        return surveyAnswer.getSeq();
    }

    public SurveyAnswer findOne(Long seq) {
        Optional<SurveyAnswer> optional = surveyAnswerRepository.findById(seq);
        SurveyAnswer surveyAnswer = optional.get();
        return surveyAnswer;
    }
}
