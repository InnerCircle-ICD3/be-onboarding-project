package com.survey.domain.surveyResponse;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextResponse {
    private static final String TEXT_ANSWER_EMPTY_EXCEPTION_MESSAGE = "텍스트 응답 내용이 비어있습니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String answer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_option_response_id")
    private SurveyOptionResponse surveyOptionResponse;

    public TextResponse(String answer) {
        validateAnswer(answer);
        this.answer = answer;
    }

    private void validateAnswer(String answer) {
        if (answer == null || answer.isBlank()) {
            throw new IllegalArgumentException(TEXT_ANSWER_EMPTY_EXCEPTION_MESSAGE);
        }
    }

    public void addSurveyOptionResponse(SurveyOptionResponse surveyOptionResponse) {
        this.surveyOptionResponse = surveyOptionResponse;
    }
}
