package com.survey.domain.surveyResponse;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChoiceResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(
            name = "choice_response_values",
            joinColumns = @JoinColumn(name = "choice_response_id")
    )
    private List<String> selectedOptions = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_option_response_id")
    private SurveyOptionResponse surveyOptionResponse;
}
