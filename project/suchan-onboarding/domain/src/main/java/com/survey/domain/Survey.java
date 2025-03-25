package com.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Survey(Long id, String title, String description, List<SurveyOption> surveyOptions) {
        this.id = id;
        this.title = title;
        this.description = description;
        validateSurveyOptionCnt(surveyOptions);
        surveyOptions.forEach(this::addSurveyOption);
    }

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

    public void modify(Survey survey) {
        if (survey.getSurveyOptions() == null || survey.getSurveyOptions().isEmpty()) {
            return;
        }

        if (survey.getSurveyOptions().size() > 10) {
            throw new IllegalArgumentException(SURVEY_OPTIONS_CNT_EXCEPTION_MESSAGE);
        }

        this.title = survey.getTitle();
        this.description = survey.getDescription();

        Map<Long, SurveyOption> existingOptionsMap = this.surveyOptions.stream()
                .filter(option -> option.getId() != null)
                .collect(Collectors.toMap(SurveyOption::getId, option -> option, (a, b) -> b));

        List<SurveyOption> updatedOptions = new ArrayList<>();

        for (SurveyOption newOption : survey.getSurveyOptions()) {
            if (newOption.getId() != null && existingOptionsMap.containsKey(newOption.getId())) {
                SurveyOption existingOption = existingOptionsMap.get(newOption.getId());
                existingOption.update(newOption);
                updatedOptions.add(existingOption);
                existingOptionsMap.remove(newOption.getId());
            } else {
                newOption.addSurvey(this);
                updatedOptions.add(newOption);
            }
        }

        this.surveyOptions.clear();
        this.surveyOptions.addAll(updatedOptions);
    }

//    public void cancelOptions(List<Long> deletedSurveyOptionIds) {
//        for (Long deletedSurveyOptionId : deletedSurveyOptionIds) {
//            this.surveyOptions.removeIf(surveyOption -> surveyOption.isSameIdentity(deletedSurveyOptionId));
//        }
//    }
}
