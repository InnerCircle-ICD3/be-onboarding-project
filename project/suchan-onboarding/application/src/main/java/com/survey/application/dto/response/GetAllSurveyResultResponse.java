package com.survey.application.dto.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllSurveyResultResponse {
    @NotNull
    private Long surveyId;

    @NotNull
    private Long version;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @NotEmpty
    @Valid
    private List<SurveyOptionResultDto> surveyOptions;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SurveyOptionResultDto {
        @NotNull
        private Long id;

        @NotNull
        @NotEmpty
        private String title;

        @NotNull
        @NotEmpty
        private String description;

        @NotNull
        private Boolean isNecessary;

        @NotNull
        @NotEmpty
        @Valid
        private InputFormResultDto inputForm;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InputFormResultDto {
        private static final String WRONG_SURVEY_OPTION_RESPONSE_TYPE_EXCEPTION_MESSAGE = "잘못된 타입의 설문 받을 항목의 응답입니다.";

        @NotNull
        private Long id;

        @NotNull
        @NotEmpty
        private String question;

        private TextResultDto textResult;
        private ChoiceResultDto choiceResult;

        public InputFormResultDto(Long id, String question, TextResultDto textResult) {
            this.id = id;
            this.question = question;
            this.textResult = textResult;
        }

        public InputFormResultDto(Long id, String question, ChoiceResultDto choiceResult) {
            this.id = id;
            this.question = question;
            this.choiceResult = choiceResult;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TextResultDto {
        @NotNull
        private Long id;

        @NotNull
        @NotEmpty
        private String textType;

        @NotNull
        @NotEmpty
        private String answer;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChoiceResultDto {
        @NotNull
        private Long id;

        @NotNull
        @NotEmpty
        private String choiceType;

        @NotNull
        @NotEmpty
        private List<String> options;

        @NotNull
        @NotEmpty
        private List<String> selectedOptions;

    }
}
