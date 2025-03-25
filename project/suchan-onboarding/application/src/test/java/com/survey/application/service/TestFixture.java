package com.survey.application.service;

import com.survey.application.dto.*;
import com.survey.application.request.CreateSurveyRequest;

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

    private static SurveyOptionDto createBasicInfoSurveyOption() {
        return new SurveyOptionDto(
                "기본 정보",
                "응답자의 기본 정보입니다.",
                true,
                List.of(
                        createNameInputForm(),
                        createAgeGroupInputForm()
                )
        );
    }

    private static SurveyOptionDto createSatisfactionSurveyOption() {
        return new SurveyOptionDto(
                "서비스 만족도",
                "서비스에 대한 만족도를 평가해주세요.",
                true,
                List.of(
                        createSatisfactionScoreInputForm(),
                        createFeedbackInputForm()
                )
        );
    }

    private static InputFormDto createNameInputForm() {
        return new InputFormDto(
                "이름을 입력해주세요.",
                new TextInputFormDto("단답형"),
                null
        );
    }

    private static InputFormDto createAgeGroupInputForm() {
        return new InputFormDto(
                "연령대를 선택해주세요.",
                null,
                new ChoiceInputFormDto(
                        "단일",
                        List.of("10대", "20대", "30대", "40대", "50대 이상")
                )
        );
    }

    private static InputFormDto createSatisfactionScoreInputForm() {
        return new InputFormDto(
                "전반적인 서비스 만족도를 선택해주세요.",
                null,
                new ChoiceInputFormDto(
                        "다중",
                        List.of("매우 불만족", "불만족", "보통", "만족", "매우 만족")
                )
        );
    }

    private static InputFormDto createFeedbackInputForm() {
        return new InputFormDto(
                "개선을 위한 의견을 자유롭게 작성해주세요.",
                new TextInputFormDto("장문형"),
                null
        );
    }

}
