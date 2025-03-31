package com.survey.domain.surveyResponse;

import com.survey.domain.survey.ChoiceType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChoiceResponse {
    private static final String SELECTED_OPTION_EMPTY_EXCEPTION_MESSAGE = "선택된 항목이 비어있습니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChoiceType choiceType;

    @ElementCollection
    @CollectionTable(
            name = "choice_response_values",
            joinColumns = @JoinColumn(name = "choice_response_id")
    )
    private List<String> selectedOptions = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_option_response_id")
    private SurveyOptionResponse surveyOptionResponse;

    public ChoiceResponse(ChoiceType choiceType, List<String> selectedOptions) {
        validateSelectedOptions(selectedOptions);
        this.choiceType = choiceType;
        this.selectedOptions = selectedOptions;
    }

    private void validateSelectedOptions(List<String> selectedOptions) {
        if (selectedOptions == null || selectedOptions.isEmpty()) {
            throw new IllegalArgumentException(SELECTED_OPTION_EMPTY_EXCEPTION_MESSAGE);
        }
    }

    public void addSurveyOptionResponse(SurveyOptionResponse surveyOptionResponse) {
        this.surveyOptionResponse = surveyOptionResponse;
    }
}
