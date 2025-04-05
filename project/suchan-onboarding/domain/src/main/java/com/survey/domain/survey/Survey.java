package com.survey.domain.survey;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @Version
    @Column(nullable = false)
    private Long version = 1L;

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
        this.version = 1L;
        validateSurveyOptionCnt(surveyOptions);
        surveyOptions.forEach(this::addSurveyOption);
    }

    public Survey(String title, String description, List<SurveyOption> surveyOptions) {
        this.title = title;
        this.description = description;
        this.version = 1L;
        validateSurveyOptionCnt(surveyOptions);
        surveyOptions.forEach(this::addSurveyOption);
    }

    private void validateSurveyOptionCnt(List<SurveyOption> surveyOptions) {
        if (surveyOptions == null || surveyOptions.isEmpty() || checkActivatedSurveyOptionsSizeOver(surveyOptions)) {
            throw new IllegalArgumentException(SURVEY_OPTIONS_CNT_EXCEPTION_MESSAGE);
        }
    }

    private boolean checkActivatedSurveyOptionsSizeOver(List<SurveyOption> surveyOptions) {
        List<SurveyOption> activatedSurveyOptions = surveyOptions.stream().filter(SurveyOption::isActivated).toList();
        return activatedSurveyOptions.size() > 10;
    }

    private void addSurveyOption(SurveyOption surveyOption) {
        this.surveyOptions.add(surveyOption);
        surveyOption.addSurvey(this);
    }

    public void changeId(long idIndex) {
        this.id = idIndex;
    }

    public void modify(Survey survey) {
        validateSurveyOptionCnt(survey.getSurveyOptions());
        this.title = survey.getTitle();
        this.description = survey.getDescription();

        Map<Long, SurveyOption> existingOptionsMap = this.surveyOptions.stream()
                .filter(option -> option.getId() != null)
                .collect(Collectors.toMap(SurveyOption::getId, option -> option, (a, b) -> b));

        List<SurveyOption> updatedOptions = new ArrayList<>();

        for (SurveyOption newOption : survey.getSurveyOptions()) {
            if (newOption.getId() != null && existingOptionsMap.containsKey(newOption.getId())) {
                changeSurveyOption(newOption, existingOptionsMap, updatedOptions);
            } else {
                registerSurveyOption(newOption, updatedOptions);
            }
        }

        List<SurveyOption> optionsToRemove = new ArrayList<>();
        for (SurveyOption surveyOption : this.surveyOptions) {
            if (!updatedOptions.contains(surveyOption)) {
                optionsToRemove.add(surveyOption);
            }
        }

        removeSurveyOption(optionsToRemove);
    }

    private void changeSurveyOption(SurveyOption newOption, Map<Long, SurveyOption> existingOptionsMap, List<SurveyOption> updatedOptions) {
        SurveyOption existingOption = existingOptionsMap.get(newOption.getId());
        existingOption.modifyOption(newOption);
        updatedOptions.add(existingOption);
    }

    private void registerSurveyOption(SurveyOption newOption, List<SurveyOption> updatedOptions) {
        addSurveyOption(newOption);
        updatedOptions.add(newOption);
    }

    private void removeSurveyOption(List<SurveyOption> surveyOptions) {
        for (SurveyOption surveyOption : surveyOptions) {
            surveyOption.delete();
        }
    }

    public static Survey createTestSurvey(Long id, Long version, String title, String description, List<SurveyOption> surveyOptions) {
        Survey survey = new Survey(id, title, description, surveyOptions);
        survey.id = id;
        survey.version = version;
        return survey;
    }

    public int getActivatedOptionSize() {
        return (int) this.surveyOptions.stream()
                .filter(SurveyOption::isActivated)
                .count();
    }

    public int getDeletedOptionSize() {
        return (int) this.surveyOptions.stream()
                .filter(SurveyOption::isDeleted)
                .count();
    }

}
