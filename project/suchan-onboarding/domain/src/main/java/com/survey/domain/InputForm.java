package com.survey.domain;

import jakarta.persistence.*;

@Entity
public class InputForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_option_id")
    private SurveyOption surveyOption;

    @OneToOne(mappedBy = "inputForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private TextInputForm textInputForm;

    @OneToOne(mappedBy = "inputForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private ChoiceInputForm choiceInputForm;
}
