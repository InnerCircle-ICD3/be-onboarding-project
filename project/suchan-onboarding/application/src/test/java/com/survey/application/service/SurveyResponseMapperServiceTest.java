package com.survey.application.service;

import com.survey.application.dto.response.GetAllSurveyResultResponse;
import com.survey.application.dto.response.GetAllSurveyResultResponse.ChoiceResultDto;
import com.survey.application.dto.response.GetAllSurveyResultResponse.InputFormResultDto;
import com.survey.application.dto.response.GetAllSurveyResultResponse.SurveyOptionResultDto;
import com.survey.application.dto.response.GetAllSurveyResultResponse.TextResultDto;
import com.survey.application.test.TestFixture;
import com.survey.application.test.TestSurveyResultResponseComparator;
import com.survey.domain.survey.Survey;
import com.survey.domain.survey.SurveyOption;
import com.survey.domain.surveyResponse.SurveyOptionResponse;
import com.survey.domain.surveyResponse.SurveyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SurveyResponseMapperServiceTest {

    private SurveyResponseMapperService surveyResponseMapperService;

    @BeforeEach
    void setUp() {
        surveyResponseMapperService = new SurveyResponseMapperService();
    }

    @Test
    @DisplayName("텍스트 형 설문 항목이 있는 설문 조사와 설문 조사 응답으로 설문 조사 최종 결과를 생성할 수 있다.")
    void generate_with_text_type() {
        // given
        SurveyOption shortOption = TestFixture.createShortTextSurveyOption(true);
        SurveyOption longOption = TestFixture.createLongTextSurveyOption(true);
        Survey survey = new Survey(1L, "제목", "설명", List.of(shortOption, longOption));

        SurveyOptionResponse shortResponse = TestFixture.createShortTextSurveyOptionResponse();
        SurveyOptionResponse longResponse = TestFixture.createLongTextSurveyOptionResponse();
        SurveyResponse surveyResponse = new SurveyResponse(survey.getId(), 1L, LocalDateTime.now(), List.of(shortResponse, longResponse));

        // when
        GetAllSurveyResultResponse result = surveyResponseMapperService.generate(survey, surveyResponse);

        // then
        TestSurveyResultResponseComparator.areEqual(new GetAllSurveyResultResponse(
                1L, 1L, "제목", "설명", List.of(
                new SurveyOptionResultDto(1L, "설문 항목1", "설명1", true,
                        new InputFormResultDto(1L, "질문1", new TextResultDto(1L, "단문형", "short answer"))),
                new SurveyOptionResultDto(2L, "설문 항목2", "설명2", true,
                        new InputFormResultDto(2L, "질문2", new TextResultDto(2L, "장문형", "long answer")))
        )), result);
    }

    @Test
    @DisplayName("선택 형 설문 항목이 있는 설문 조사와 설문 조사 응답으로 설문 조사 최종 결과를 생성할 수 있다.")
    void generate_with_option_type() {
        // given
        SurveyOption singleOption = TestFixture.createSingleChoiceSurveyOption(true);
        SurveyOption multipleOption = TestFixture.createMultipleChoiceSurveyOption(true);
        Survey survey = new Survey(1L, "제목", "설명", List.of(singleOption, multipleOption));

        SurveyOptionResponse shortResponse = TestFixture.createSingleChoiceSurveyOptionResponse();
        SurveyOptionResponse longResponse = TestFixture.createMultipleChoiceSurveyOptionResponse();
        SurveyResponse surveyResponse = new SurveyResponse(survey.getId(), 1L, LocalDateTime.now(), List.of(shortResponse, longResponse));

        // when
        GetAllSurveyResultResponse result = surveyResponseMapperService.generate(survey, surveyResponse);

        // then
        TestSurveyResultResponseComparator.areEqual(new GetAllSurveyResultResponse(
                1L, 1L, "제목", "설명", List.of(
                new SurveyOptionResultDto(3L, "설문 항목3", "설명3", true,
                        new InputFormResultDto(3L, "질문3", new ChoiceResultDto(3L, "단일",
                                List.of(
                                        "선택지1",
                                        "선택지2",
                                        "선택지3"),
                                List.of("선택지1"))
                        )),
                new SurveyOptionResultDto(4L, "설문 항목4", "설명4", true,
                        new InputFormResultDto(4L, "질문4", new ChoiceResultDto(4L, "다중",
                                List.of(
                                        "선택지1",
                                        "선택지2",
                                        "선택지3"),
                                List.of("선택지1"))
                        ))
        )), result);
    }

    @Test
    @DisplayName("비활성화된 설문 항목은 최종 결과에 포함되지 않는다.")
    void deleted_service_option_case() {
        // given
        SurveyOption singleOption = TestFixture.createSingleChoiceSurveyOption(true);
        SurveyOption multipleOption = TestFixture.createMultipleChoiceSurveyOption(true);
        Survey survey = new Survey(1L, "제목", "설명", List.of(singleOption, multipleOption));

        SurveyOptionResponse shortResponse = TestFixture.createSingleChoiceSurveyOptionResponse();
        SurveyOptionResponse longResponse = TestFixture.createMultipleChoiceSurveyOptionResponse();
        SurveyResponse surveyResponse = new SurveyResponse(survey.getId(), 1L, LocalDateTime.now(), List.of(shortResponse, longResponse));

        for (SurveyOption surveyOption : survey.getSurveyOptions()) {
            surveyOption.delete();
        }

        // when
        GetAllSurveyResultResponse result = surveyResponseMapperService.generate(survey, surveyResponse);

        // then
        assertThat(result.getSurveyOptions().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("설문 항목 id에 해당하는 설문 항목 응답이 없는 경우 예외가 발생한다.")
    void unsupported_option_exception_case() {
        // given
        SurveyOption shortOption = TestFixture.createShortTextSurveyOption(true);
        SurveyOption longOption = TestFixture.createLongTextSurveyOption(true);
        Survey survey = new Survey(1L, "제목", "설명", List.of(shortOption, longOption));

        SurveyOptionResponse shortResponse = TestFixture.createShortTextSurveyOptionResponse();
        SurveyOptionResponse longResponse = TestFixture.createLongTextSurveyOptionResponse();
        SurveyOptionResponse unSupportedLongResponse = TestFixture.createUnSupportedLongTextSurveyOptionResponse();
        SurveyResponse surveyResponse = new SurveyResponse(survey.getId(), 1L, LocalDateTime.now(), List.of(shortResponse, longResponse, unSupportedLongResponse));

        // when // then
        assertThatThrownBy(() -> surveyResponseMapperService.generate(survey, surveyResponse))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("설문지에 존재하지 않는 항목에 대한 응답입니다 " + unSupportedLongResponse.getSurveyOptionId());
    }

    @Test
    @DisplayName("설문 항목 id에 해당하는 설문 항목 응답이 없는 경우 예외가 발생한다.")
    void necessary_option_exception_case() {
        // given
        SurveyOption shortOption = TestFixture.createShortTextSurveyOption(true);
        SurveyOption longOption = TestFixture.createLongTextSurveyOption(true);
        Survey survey = new Survey(1L, "제목", "설명", List.of(shortOption, longOption));

        SurveyOptionResponse shortResponse = TestFixture.createShortTextSurveyOptionResponse();
        SurveyResponse surveyResponse = new SurveyResponse(survey.getId(), 1L, LocalDateTime.now(), List.of(shortResponse));

        // when // then
        assertThatThrownBy(() -> surveyResponseMapperService.generate(survey, surveyResponse))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("필수 설문 항목에 대한 응답이 없습니다 " + longOption.getId());
    }

}