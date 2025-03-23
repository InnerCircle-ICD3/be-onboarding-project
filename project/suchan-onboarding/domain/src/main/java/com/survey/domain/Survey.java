package com.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Survey {

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
        surveyOptions.forEach(this::addSurveyOption);
    }

    private void addSurveyOption(SurveyOption surveyOption) {
        this.surveyOptions.add(surveyOption);
        surveyOption.addSurvey(this);
    }
}
