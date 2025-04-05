package com.survey.application.test;

import com.survey.application.dto.dto.*;
import com.survey.application.dto.request.CreateSurveyRequest;
import com.survey.application.dto.request.UpdateSurveyRequest;
import com.survey.domain.survey.*;
import com.survey.domain.surveyResponse.ChoiceResponse;
import com.survey.domain.surveyResponse.SurveyOptionResponse;
import com.survey.domain.surveyResponse.SurveyResponse;
import com.survey.domain.surveyResponse.TextResponse;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class TestFixture {

    public static CreateSurveyRequest createDefaultSurveyRequest() {
        return new CreateSurveyRequest(
                "만족도 설문조사",
                "서비스 이용 경험에 대한 설문조사입니다.",
                List.of(
                        createBasicInfoSurveyOption(),
                        createSatisfactionSurveyOption()
                )
        );
    }

    private static CreateSurveyOptionDto createBasicInfoSurveyOption() {
        return new CreateSurveyOptionDto(
                "기본 정보",
                "응답자의 기본 정보입니다.",
                true,
                createNameInputForm()
        );
    }

    private static CreateSurveyOptionDto createSatisfactionSurveyOption() {
        return new CreateSurveyOptionDto(
                "서비스 만족도",
                "서비스에 대한 만족도를 평가해주세요.",
                true,
                createFeedbackInputForm()
        );
    }

    private static CreateInputFormDto createNameInputForm() {
        return new CreateInputFormDto(
                "이름을 입력해주세요.",
                new CreateTextInputFormDto("단답형"),
                null
        );
    }

    private static CreateInputFormDto createFeedbackInputForm() {
        return new CreateInputFormDto(
                "개선을 위한 의견을 자유롭게 작성해주세요.",
                new CreateTextInputFormDto("장문형"),
                null
        );
    }

    public static UpdateSurveyRequest updateDefaultSurveyRequest(Long surveyId) {
        return new UpdateSurveyRequest(
                surveyId,
                "개선된 고객 만족도 조사",
                "저희 서비스 이용 경험에 대한 귀하의 소중한 의견을 들려주세요. (2025년 3월 업데이트)",
                List.of(
                        updatedBasicInfoSurveyOption(),
                        updatedServiceQualityOption(),
                        updatedImprovementOption(),
                        updatedRecommendationOption()
                )
        );
    }

    private static UpdateSurveyOptionDto updatedBasicInfoSurveyOption() {
        return new UpdateSurveyOptionDto(
                1L,
                "개인 정보",
                "통계 목적으로만 사용되며, 개인 식별에 활용되지 않습니다.",
                true,
                new UpdateInputFormDto(
                        "귀하의 연령대는 어떻게 되시나요?",
                        null,
                        new UpdateChoiceInputFormDto(
                                "단일",
                                List.of("10대 이하", "20대", "30대", "40대", "50대", "60대 이상")
                        )
                )
        );
    }

    private static UpdateSurveyOptionDto updatedServiceQualityOption() {
        return new UpdateSurveyOptionDto(
                2L,
                "서비스 품질",
                "저희 서비스의 품질에 대한 평가입니다.",
                true,
                new UpdateInputFormDto(
                        "서비스의 전반적인 만족도는 어떠신가요?",
                        null,
                        new UpdateChoiceInputFormDto(
                                "단일",
                                List.of("매우 불만족", "불만족", "보통", "만족", "매우 만족")
                        )
                )
        );
    }

    private static UpdateSurveyOptionDto updatedImprovementOption() {
        return new UpdateSurveyOptionDto(
                3L,
                "개선사항",
                "저희 서비스의 개선이 필요한 부분에 대한 의견입니다.",
                false,
                new UpdateInputFormDto(
                        "개선이 필요하다고 생각되는 부분을 자유롭게 작성해주세요.",
                        new UpdateTextInputFormDto("장문형"),
                        null
                )
        );
    }

    private static UpdateSurveyOptionDto updatedRecommendationOption() {
        return new UpdateSurveyOptionDto(
                4L,
                "추천 의향",
                "저희 서비스를 다른 사람에게 추천할 의향이 있으신지 알려주세요.",
                true,
                new UpdateInputFormDto(
                        "저희 서비스를 지인에게 추천할 의향이 있으신가요?",
                        null,
                        new UpdateChoiceInputFormDto(
                                "단일",
                                List.of("전혀 없음", "별로 없음", "보통", "약간 있음", "매우 있음")
                        )
                )
        );
    }

    public static UpdateSurveyRequest updateSurveyWithAdditionalTwoSurveyOptionsRequest(Long surveyId) {
        return new UpdateSurveyRequest(
                surveyId,
                "개선된 고객 만족도 조사",
                "저희 서비스 이용 경험에 대한 귀하의 소중한 의견을 들려주세요. (2025년 3월 업데이트)",
                List.of(
                        updateSameBasicInfoSurveyOption(),
                        updateSameSatisfactionSurveyOption(),
                        updatedImprovementOption(),
                        updatedRecommendationOption()
                )
        );
    }

    private static UpdateSurveyOptionDto updateSameBasicInfoSurveyOption() {
        return new UpdateSurveyOptionDto(
                1L,
                "기본 정보",
                "응답자의 기본 정보입니다.",
                true,
                updateNameInputForm()
        );
    }

    private static UpdateSurveyOptionDto updateSameSatisfactionSurveyOption() {
        return new UpdateSurveyOptionDto(
                2L,
                "서비스 만족도",
                "서비스에 대한 만족도를 평가해주세요.",
                true,
                updateFeedbackInputForm()
        );
    }

    private static UpdateInputFormDto updateNameInputForm() {
        return new UpdateInputFormDto(
                "이름을 입력해주세요.",
                new UpdateTextInputFormDto("단답형"),
                null
        );
    }

    private static UpdateInputFormDto updateFeedbackInputForm() {
        return new UpdateInputFormDto(
                "개선을 위한 의견을 자유롭게 작성해주세요.",
                new UpdateTextInputFormDto("장문형"),
                null
        );
    }

    public static SurveyOption createShortTextSurveyOption(boolean isNecessary) {
        return new SurveyOption(1L, "설문 항목1", "설명1", isNecessary, new InputForm(1L, "질문1", new TextInputForm(1L, TextType.SHORT)));
    }

    public static SurveyOption createLongTextSurveyOption(boolean isNecessary) {
        return new SurveyOption(2L, "설문 항목2", "설명2", isNecessary, new InputForm(2L, "질문2", new TextInputForm(2L, TextType.LONG)));
    }

    public static SurveyOption createSingleChoiceSurveyOption(boolean isNecessary) {
        return new SurveyOption(3L, "설문 항목3",
                "설명3",
                isNecessary,
                new InputForm(3L, "질문3", new ChoiceInputForm(3L, ChoiceType.SINGLE, List.of(
                        new InputOption("선택지1"),
                        new InputOption("선택지2"),
                        new InputOption("선택지3")
                )))
        );
    }

    public static SurveyOption createMultipleChoiceSurveyOption(boolean isNecessary) {
        return new SurveyOption(
                4L,
                "설문 항목4",
                "설명4",
                isNecessary,
                new InputForm(
                        4L,
                        "질문4", new ChoiceInputForm(
                        4L,
                        ChoiceType.MULTIPLE,
                        List.of(
                                new InputOption("선택지1"),
                                new InputOption("선택지2"),
                                new InputOption("선택지3")
                        )
                )
                )
        );
    }


    public static SurveyOptionResponse createShortTextSurveyOptionResponse() {
        return new SurveyOptionResponse(1L, new TextResponse(TextType.SHORT, "short answer"));
    }

    public static SurveyOptionResponse createLongTextSurveyOptionResponse() {
        return new SurveyOptionResponse(2L, new TextResponse(TextType.LONG, "long answer"));
    }

    public static SurveyOptionResponse createSingleChoiceSurveyOptionResponse() {
        return new SurveyOptionResponse(
                3L,
                new ChoiceResponse(ChoiceType.SINGLE, List.of("선택지1"))
        );
    }

    public static SurveyOptionResponse createMultipleChoiceSurveyOptionResponse() {
        return new SurveyOptionResponse(
                4L,
                new ChoiceResponse(ChoiceType.MULTIPLE, List.of(
                        "선택지1",
                        "선택지2",
                        "선택지3")
                )
        );
    }

    public static SurveyOptionResponse createUnSupportedLongTextSurveyOptionResponse() {
        return new SurveyOptionResponse(5L, new TextResponse(TextType.LONG, "very long answer"));
    }

}
