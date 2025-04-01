package org.innercircle.service;

import org.innercircle.repository.SurveyJpaRepository;
import org.innercircle.entity.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
// @Transactional 을 붙이지 않으면 영속성 컨텍스트가 닫힌 상태에서 지연 로딩을 시도하게 되므로 아래와 같은 에러가 발생
// org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: org.innercircle.entity.Survey.surveyItemList: could not initialize proxy - no Session
public class SurveyService {

    @Autowired
    private SurveyJpaRepository surveyRepository;
//    private SurveySpringRepository surveyRepository;

//    public Long createSurvey(String surveyTitle, String surveyDesc, List<SurveyItem> surveyItemList) {
//        // 주어진 데이터를 바탕으로 entity 를 생성하는 건 service 계층이 아니라 entity 계층에서 담당할 기능
//        return 1L;
//    }


    public Long saveSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public Survey findSurvey(Long seq) {
        Survey survey = surveyRepository.findOne(seq);
        return survey;
    }
}
