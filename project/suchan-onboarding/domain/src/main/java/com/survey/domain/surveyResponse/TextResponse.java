package com.survey.domain.surveyResponse;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TextResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String answer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_option_response_id")
    private SurveyOptionResponse surveyOptionResponse;
}
