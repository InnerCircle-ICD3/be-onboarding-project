package com.survey.domain.survey;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToOne(mappedBy = "surveyOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private InputForm inputForm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SurveyOptionStatus status;

    public SurveyOption(String title, String description, boolean isNecessary, InputForm inputForm) {
        this.title = title;
        this.description = description;
        this.isNecessary = isNecessary;
        addInputForm(inputForm);
    }

    public SurveyOption(Long id, String title, String description, boolean isNecessary, InputForm inputForm) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isNecessary = isNecessary;
        addInputForm(inputForm);
    }

    public void addSurvey(Survey survey) {
        this.survey = survey;
    }

    public void modifyOption(SurveyOption surveyOption) {
        this.title = surveyOption.title;
        this.description = surveyOption.description;
        this.isNecessary = surveyOption.isNecessary;
        if (this.inputForm.isNeededModify(surveyOption.inputForm)) {
            this.inputForm.modifyInputForm(surveyOption.inputForm);
        }
    }

    private void addInputForm(InputForm inputForm) {
        this.inputForm = inputForm;
        inputForm.addSurveyOption(this);
    }

    public static SurveyOption createTestSurveyOption(Long id, String title, String description, boolean isNecessary, InputForm inputForm) {
        SurveyOption surveyOption = new SurveyOption(title, description, isNecessary, inputForm);
        surveyOption.id = id;
        return surveyOption;
    }

    public void delete() {
        this.status = SurveyOptionStatus.DELETED;
    }
}
