package com.survey.domain.surveyResponse;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SurveyOptionResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "survey_response_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SurveyResponse surveyResponse;

    @OneToOne(mappedBy = "survey_option_response", cascade = CascadeType.ALL, orphanRemoval = true)
    private TextResponse textResponse;

    @OneToOne(mappedBy = "survey_option_response", cascade = CascadeType.ALL, orphanRemoval = true)
    private ChoiceResponse choiceResponse;
}
