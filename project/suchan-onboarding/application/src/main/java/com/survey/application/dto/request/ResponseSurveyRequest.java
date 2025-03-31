package com.survey.application.dto.request;

import com.survey.domain.survey.ChoiceType;
import com.survey.domain.survey.TextType;
import com.survey.domain.surveyResponse.ChoiceResponse;
import com.survey.domain.surveyResponse.SurveyOptionResponse;
import com.survey.domain.surveyResponse.SurveyResponse;
import com.survey.domain.surveyResponse.TextResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSurveyRequest {
    @NotNull
    private Long surveyId;

    @NotNull
    private Long surveyVersion;

    @NotNull
    private LocalDateTime submittedAt;

    @NotNull
    @NotEmpty
    @Valid
    private List<SurveyOptionResponseDto> optionResponses;

    public SurveyResponse create() {
        return new SurveyResponse(
                surveyId,
                surveyVersion,
                submittedAt,
                optionResponses.stream()
                        .map(SurveyOptionResponseDto::create)
                        .toList());
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class SurveyOptionResponseDto {
        private static final String WRONG_SURVEY_OPTION_RESPONSE_TYPE_EXCEPTION_MESSAGE = "잘못된 타입의 설문 받을 항목의 응답입니다.";

        @NotNull
        private Long surveyOptionId;

        @Valid
        private TextResponseDto textResponseDto;

        @Valid
        private ChoiceResponseDto choiceResponseDto;

        public SurveyOptionResponse create() {
            if (textResponseDto != null) {
                return new SurveyOptionResponse(surveyOptionId, textResponseDto.create());
            } else if (choiceResponseDto != null) {
                return new SurveyOptionResponse(surveyOptionId, choiceResponseDto.create());
            } else {
                throw new IllegalStateException(WRONG_SURVEY_OPTION_RESPONSE_TYPE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class TextResponseDto {
        @NotNull
        private String textType;

        @NotNull
        private String textAnswer;

        public TextResponse create() {
            return new TextResponse(TextType.findByName(textType), textAnswer);
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class ChoiceResponseDto {
        @NotNull
        private String choiceType;

        @NotNull
        @NotEmpty
        private List<String> selectedOptions;

        public ChoiceResponse create() {
            return new ChoiceResponse(ChoiceType.findByName(choiceType), selectedOptions);
        }
    }
}