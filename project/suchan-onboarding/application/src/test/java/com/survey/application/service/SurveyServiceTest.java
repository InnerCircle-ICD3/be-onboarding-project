package com.survey.application.service;

import com.survey.application.repository.FakeSurveyRepository;
import com.survey.application.dto.request.CreateSurveyRequest;
import com.survey.application.test.TestFixture;
import com.survey.domain.Survey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class SurveyServiceTest {

    private SurveyService surveyService;
    private FakeSurveyRepository surveyRepository;

    @BeforeEach
    void setUp() {
        surveyRepository = new FakeSurveyRepository(new HashMap<>());
        surveyService = new SurveyService(surveyRepository);
    }

    @Test
    @DisplayName("설문조사를 요청 본문으로 부터 생성하고, 저장할 수 있다.")
    void create_survey() {
        // given
        CreateSurveyRequest request = TestFixture.createDefaultSurveyRequest();

        // when
        surveyService.registerSurvey(request);

        // then
        assertThat(surveyRepository.getCallCounter()).isEqualTo(1);
        Survey survey = surveyRepository.findById(1L);
        assertThat(survey.getTitle()).isEqualTo("만족도 설문조사");
        assertThat(survey.getSurveyOptions().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("설문조사를 요청 본문으로 수정할 수 있다.")
    void modify_survey() {
        // given


        // when

        // then

    }
}