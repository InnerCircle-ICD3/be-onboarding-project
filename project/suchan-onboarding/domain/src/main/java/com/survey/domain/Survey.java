package com.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Survey {
    private static final String SURVEY_OPTIONS_CNT_EXCEPTION_MESSAGE = "설문 받을 항목은 1개 ~ 10개 사이여야 합니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyOption> surveyOptions = new ArrayList<>();

    public Survey(String title, String description, List<SurveyOption> surveyOptions) {
        this.title = title;
        this.description = description;
        validateSurveyOptionCnt(surveyOptions);
        surveyOptions.forEach(this::addSurveyOption);
    }

    private void validateSurveyOptionCnt(List<SurveyOption> surveyOptions) {
        if (surveyOptions.isEmpty() || surveyOptions.size() > 10) {
            throw new IllegalArgumentException(SURVEY_OPTIONS_CNT_EXCEPTION_MESSAGE);
        }
    }

    private void addSurveyOption(SurveyOption surveyOption) {
        this.surveyOptions.add(surveyOption);
        surveyOption.addSurvey(this);
    }

    public void changeId(long idIndex) {
        this.id = idIndex;
    }

    public void update(Survey survey) {
        validateSurveyOptionCnt(survey.getSurveyOptions());
        this.title = survey.getTitle();
        this.description = survey.getDescription();

        Map<Long, SurveyOption> surveyOptionMap = new HashMap<>();
        for (SurveyOption surveyOption : survey.getSurveyOptions()) {
            surveyOptionMap.put(surveyOption.getId(), surveyOption);
        }

        surveyOptionMap.forEach((k, v) -> {
            boolean isNewSurveyOption = true;
            for (SurveyOption surveyOption : this.surveyOptions) {
                if (surveyOption.isSameIdentity(k)) {
                    isNewSurveyOption = false;
                    surveyOption.update(v);
                }
            }

            if (isNewSurveyOption) {
               surveyOptions.add(v);
            }
        });

    }
}
