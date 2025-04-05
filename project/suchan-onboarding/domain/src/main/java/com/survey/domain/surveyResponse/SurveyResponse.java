package com.survey.domain.surveyResponse;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SurveyResponse {
    private static final String SURVEY_OPTION_RESPONSES_EMPTY_EXCEPTION_MESSAGE = "응답 항목이 비어있습니다.";
    private static final String DUPLICATE_SURVEY_OPTION_RESPONSE_EXCEPTION_MESSAGE = "중복된 응답 항목에 대한 답변이 존재합니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long surveyId;

    @Column(nullable = false)
    private Long surveyVersion;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    @OneToMany(mappedBy = "surveyResponse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyOptionResponse> surveyOptionResponses = new ArrayList<>();

    public SurveyResponse(Long surveyId, Long surveyVersion, LocalDateTime submittedAt, List<SurveyOptionResponse> surveyOptionResponses) {
        validateSurveyOptionResponses(surveyOptionResponses);
        this.surveyId = surveyId;
        this.surveyVersion = surveyVersion;
        this.submittedAt = submittedAt;
        addSurveyOptionResponses(surveyOptionResponses);
    }

    private void validateSurveyOptionResponses(List<SurveyOptionResponse> surveyOptionResponses) {
        if (surveyOptionResponses == null || surveyOptionResponses.isEmpty()) {
            throw new IllegalArgumentException(SURVEY_OPTION_RESPONSES_EMPTY_EXCEPTION_MESSAGE);
        }

        List<Long> surveyOptionIds = new ArrayList<>();
        for (SurveyOptionResponse surveyOptionRes : surveyOptionResponses) {
            if (surveyOptionIds.contains(surveyOptionRes.getSurveyOptionId())) {
                throw new IllegalArgumentException(DUPLICATE_SURVEY_OPTION_RESPONSE_EXCEPTION_MESSAGE);
            }
            surveyOptionIds.add(surveyOptionRes.getSurveyOptionId());
        }
    }

    private void addSurveyOptionResponses(List<SurveyOptionResponse> surveyOptionResponses) {
        this.surveyOptionResponses.addAll(surveyOptionResponses);
        for (SurveyOptionResponse surveyOptionRes : surveyOptionResponses) {
            surveyOptionRes.addSurveyResponse(this);
        }
    }

    public boolean isSameSurveyVersion(long version) {
        return this.surveyVersion.equals(version);
    }
}
