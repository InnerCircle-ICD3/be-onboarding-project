package com.survey.domain.surveyResponse.service;

import com.survey.domain.survey.*;
import com.survey.domain.survey.repository.SurveyRepository;
import com.survey.domain.surveyResponse.ChoiceResponse;
import com.survey.domain.surveyResponse.SurveyOptionResponse;
import com.survey.domain.surveyResponse.SurveyResponse;
import com.survey.domain.surveyResponse.TextResponse;
import com.survey.test.FakeSurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SurveyResponseValidationServiceTest {

    private SurveyRepository surveyRepository;
    private SurveyResponseValidationService surveyResponseValidationService;

    @BeforeEach
    void setUp() {
        surveyRepository = new FakeSurveyRepository(new HashMap<>());
        surveyResponseValidationService = new SurveyResponseValidationService(surveyRepository);
    }

    @Test
    @DisplayName("설문조사를 찾을 수 없을 때 예외가 발생한다")
    void validate_survey_response_when_survey_not_found() {
        // given
        SurveyResponse surveyResponse = createSurveyResponse(1L, 1L);

        // when // then
        assertThatThrownBy(() -> surveyResponseValidationService.validateSurveyResponse(surveyResponse))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("설문조사를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("설문조사 버전이 일치하지 않을 때 예외가 발생한다")
    void validate_survey_response_when_survey_version_mismatch() {
        // given
        SurveyResponse surveyResponse = createSurveyResponse(1L, 1L);
        Survey survey = createSurveyWithTextOption(1L, 2L, 1L);
        surveyRepository.save(survey);

        // when // then
        assertThatThrownBy(() -> surveyResponseValidationService.validateSurveyResponse(surveyResponse))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("설문 조사 버전과 실제 설문 받은 항목에 대한 응답 버전이 동일하지 않습니다.");
    }

    @Test
    @DisplayName("필수 항목에 대한 응답이 누락되었을 때 예외가 발생한다")
    void validate_survey_response_when_necessary_option_skipped() {
        // given
        SurveyResponse surveyResponse = createSurveyResponse(1L, 1L); // requiredOptionId : 1
        Survey survey = createSurveyWithRequiredOption(1L, 1L, 2L);
        surveyRepository.save(survey);

        // when // then
        assertThatThrownBy(() -> surveyResponseValidationService.validateSurveyResponse(surveyResponse))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("필수 항목에 대한 응답이 누락되었습니다.");
    }

    @Test
    @DisplayName("유효하지 않은 설문 항목에 응답했을 때 예외가 발생한다")
    void validate_survey_response_with_invalid_survey_option() {
        // given
        SurveyOptionResponse invalidOptionResponse = new SurveyOptionResponse(999L, new TextResponse(TextType.SHORT, "테스트"));
        List<SurveyOptionResponse> optionResponses = new ArrayList<>();
        optionResponses.add(invalidOptionResponse);

        SurveyResponse surveyResponse = new SurveyResponse(1L, 1L, LocalDateTime.now(), optionResponses);
        Survey survey = createSurveyWithChoiceOption(1L, 1L, 1L);
        surveyRepository.save(survey);

        // when // then
        assertThatThrownBy(() -> surveyResponseValidationService.validateSurveyResponse(surveyResponse))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 설문 항목입니다.");
    }

    @Test
    @DisplayName("텍스트 응답이 아닌 설문 항목에 텍스트로 응답했을 때 예외가 발생한다")
    void validate_survey_response_with_wrong_text_response_type() {
        // given
        SurveyOptionResponse wrongTypeResponse = new SurveyOptionResponse(1L, new TextResponse(TextType.SHORT, "테스트"));
        List<SurveyOptionResponse> optionResponses = new ArrayList<>();
        optionResponses.add(wrongTypeResponse);

        SurveyResponse surveyResponse = new SurveyResponse(1L, 1L, LocalDateTime.now(), optionResponses);
        Survey survey = createSurveyWithChoiceOption(1L, 1L, 1L);
        surveyRepository.save(survey);

        // when // then
        assertThatThrownBy(() -> surveyResponseValidationService.validateSurveyResponse(surveyResponse))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("텍스트 응답이 아닌 설문 항목입니다.");
    }

    @Test
    @DisplayName("선택형 응답이 아닌 설문 항목에 선택형으로 응답했을 때 예외가 발생한다")
    void validate_survey_response_with_wrong_choice_response_type() {
        // given
        SurveyOptionResponse wrongTypeResponse = new SurveyOptionResponse(1L, new ChoiceResponse(ChoiceType.SINGLE, List.of("선택")));
        List<SurveyOptionResponse> optionResponses = new ArrayList<>();
        optionResponses.add(wrongTypeResponse);

        SurveyResponse surveyResponse = new SurveyResponse(1L, 1L, LocalDateTime.now(), optionResponses);
        Survey survey = createSurveyWithTextOption(1L, 1L, 1L);
        surveyRepository.save(survey);

        // when // then
        assertThatThrownBy(() -> surveyResponseValidationService.validateSurveyResponse(surveyResponse))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("선택형 응답이 아닌 설문 항목입니다.");
    }

    @Test
    @DisplayName("유효하지 않은 선택지로 응답했을 때 예외가 발생한다")
    void validate_survey_response_with_invalid_selected_option() {
        // given
        SurveyOptionResponse invalidOptionResponse = new SurveyOptionResponse(1L, new ChoiceResponse(ChoiceType.SINGLE, List.of("없는 선택지")));
        List<SurveyOptionResponse> optionResponses = new ArrayList<>();
        optionResponses.add(invalidOptionResponse);

        SurveyResponse surveyResponse = new SurveyResponse(1L, 1L, LocalDateTime.now(), optionResponses);
        Survey survey = createSurveyWithValidChoiceOptions(1L, 1L, 1L);
        surveyRepository.save(survey);

        // when // then
        assertThatThrownBy(() -> surveyResponseValidationService.validateSurveyResponse(surveyResponse))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 선택지입니다");
    }

    @Test
    @DisplayName("단일 선택 항목에 여러 선택지를 선택했을 때 예외가 발생한다")
    void validate_survey_response_with_multiple_choices_for_single_choice_type() {
        // given
        SurveyOptionResponse multipleChoicesResponse = new SurveyOptionResponse(1L, new ChoiceResponse(ChoiceType.SINGLE, List.of("선택1", "선택2")));
        List<SurveyOptionResponse> optionResponses = new ArrayList<>();
        optionResponses.add(multipleChoicesResponse);

        SurveyResponse surveyResponse = new SurveyResponse(1L, 1L, LocalDateTime.now(), optionResponses);
        Survey survey = createSurveyWithValidChoiceOptions(1L, 1L, 1L);
        surveyRepository.save(survey);

        // when // then
        assertThatThrownBy(() -> surveyResponseValidationService.validateSurveyResponse(surveyResponse))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("단일 선택 항목에는 하나의 옵션만 선택해야 합니다.");
    }

    private SurveyResponse createSurveyResponse(Long surveyId, Long surveyVersion) {
        SurveyOptionResponse optionResponse = new SurveyOptionResponse(1L, new TextResponse(TextType.SHORT, "테스트"));
        List<SurveyOptionResponse> optionResponses = new ArrayList<>();
        optionResponses.add(optionResponse);

        return new SurveyResponse(surveyId, surveyVersion, LocalDateTime.now(), optionResponses);
    }

    private Survey createSurveyWithRequiredOption(Long surveyId, Long version, Long requiredOptionId) {
        SurveyOption requiredOption = createSurveyOption(requiredOptionId, "필수 항목", true);
        List<SurveyOption> options = new ArrayList<>();
        options.add(requiredOption);

        return Survey.createTestSurvey(surveyId, version, "테스트 설문", "설명", options);
    }

    private Survey createSurveyWithChoiceOption(Long surveyId, Long version, Long optionId) {
        InputForm inputForm = createChoiceInputForm("질문");
        SurveyOption option = createSurveyOption(optionId, "선택형 항목", false, inputForm);

        return Survey.createTestSurvey(surveyId, version, "테스트 설문", "설명", List.of(option));
    }

    private Survey createSurveyWithTextOption(Long surveyId, Long version, Long optionId) {
        InputForm inputForm = createTextInputForm("질문");
        SurveyOption option = createSurveyOption(optionId, "텍스트 항목", false, inputForm);

        return Survey.createTestSurvey(surveyId, version, "테스트 설문", "설명", List.of(option));
    }

    private Survey createSurveyWithValidChoiceOptions(Long surveyId, Long version, Long optionId) {
        List<InputOption> inputOptions = List.of(new InputOption("선택1"), new InputOption("선택2"));
        ChoiceInputForm choiceInputForm = new ChoiceInputForm(ChoiceType.SINGLE, inputOptions);
        InputForm inputForm = new InputForm("질문", choiceInputForm);
        SurveyOption option = createSurveyOption(optionId, "선택형 항목", false, inputForm);

        return Survey.createTestSurvey(surveyId, version, "테스트 설문", "설명", List.of(option));
    }

    private SurveyOption createSurveyOption(Long id, String title, boolean isNecessary) {
        InputForm inputForm = createTextInputForm("질문");
        return SurveyOption.createTestSurveyOption(id, title, "설명", isNecessary, inputForm);
    }

    private SurveyOption createSurveyOption(Long id, String title, boolean isNecessary, InputForm inputForm) {
        return SurveyOption.createTestSurveyOption(id, title, "설명", isNecessary, inputForm);
    }

    private InputForm createTextInputForm(String question) {
        return new InputForm(question, new TextInputForm(TextType.SHORT));
    }

    private InputForm createChoiceInputForm(String question) {
        List<InputOption> inputOptions = List.of(new InputOption("선택1"), new InputOption("선택2"));
        return new InputForm(question, new ChoiceInputForm(ChoiceType.SINGLE, inputOptions));
    }

}