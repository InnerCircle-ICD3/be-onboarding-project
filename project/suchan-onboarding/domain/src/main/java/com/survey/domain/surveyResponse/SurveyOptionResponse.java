package com.survey.domain.surveyResponse;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SurveyOptionResponse {
    private static final String DOUBLE_RESPONSE_TYPE_EXCEPTION_MESSAGE = "하나의 응답에 텍스트와 선택형 응답이 모두 존재할 수 없습니다.";
    private static final String RESPONSE_EMPTY_EXCEPTION_MESSAGE = "응답 값이 존재하지 않습니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long surveyOptionId;

    @JoinColumn(name = "survey_response_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SurveyResponse surveyResponse;

    @OneToOne(mappedBy = "surveyOptionResponse", cascade = CascadeType.ALL, orphanRemoval = true)
    private TextResponse textResponse;

    @OneToOne(mappedBy = "surveyOptionResponse", cascade = CascadeType.ALL, orphanRemoval = true)
    private ChoiceResponse choiceResponse;

    public SurveyOptionResponse(Long surveyOptionId, TextResponse textResponse) {
        this.surveyOptionId = surveyOptionId;
        addTextResponse(textResponse);
    }

    public SurveyOptionResponse(Long surveyOptionId, ChoiceResponse choiceResponse) {
        this.surveyOptionId = surveyOptionId;
        addChoiceResponse(choiceResponse);
    }

    private void addTextResponse(TextResponse textResponse) {
        this.textResponse = textResponse;
        textResponse.addSurveyOptionResponse(this);
    }

    private void addChoiceResponse(ChoiceResponse choiceResponse) {
        this.choiceResponse = choiceResponse;
        choiceResponse.addSurveyOptionResponse(this);
    }

    public void addSurveyResponse(SurveyResponse surveyResponse) {
        this.surveyResponse = surveyResponse;
    }

    public void validateResponseType() {
        if (hasTextResponse() && hasChoiceResponse()) {
            throw new IllegalArgumentException(DOUBLE_RESPONSE_TYPE_EXCEPTION_MESSAGE);
        }

        if (!hasTextResponse() && !hasChoiceResponse()) {
            throw new IllegalArgumentException(RESPONSE_EMPTY_EXCEPTION_MESSAGE);
        }
    }

    public boolean hasTextResponse() {
        return textResponse != null;
    }

    public boolean hasChoiceResponse() {
        return choiceResponse != null;
    }

}
