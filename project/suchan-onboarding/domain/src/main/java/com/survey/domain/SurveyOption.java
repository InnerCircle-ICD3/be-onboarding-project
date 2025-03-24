package com.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SurveyOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean isNecessary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @OneToMany(mappedBy = "surveyOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InputForm> inputForms = new ArrayList<>();

    public SurveyOption(String title, String description, boolean isNecessary, List<InputForm> inputForms) {
        this.title = title;
        this.description = description;
        this.isNecessary = isNecessary;
        inputForms.forEach(this::addInputForm);
    }

    public void addSurvey(Survey survey) {
        this.survey = survey;
    }

    private void addInputForm(InputForm inputForm) {
        this.inputForms.add(inputForm);
        inputForm.addSurveyOption(this);
    }
}
