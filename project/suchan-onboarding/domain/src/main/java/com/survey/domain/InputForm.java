package com.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public InputForm(String question, TextInputForm textInputForm, ChoiceInputForm choiceInputForm) {
        validateInputForm(textInputForm, choiceInputForm);
        this.question = question;
        addTextInputForm(textInputForm);
        addChoiceInputForm(choiceInputForm);
    }

    private void validateInputForm(TextInputForm textInputForm, ChoiceInputForm choiceInputForm) {
        if (textInputForm == null && choiceInputForm == null) {
            throw new IllegalArgumentException("최소 하나의 입력 형태는 존재해야 합니다.");
        }

        if (textInputForm != null && choiceInputForm != null) {
            throw new IllegalArgumentException("입력 형태가 2가지일 수는 없습니다.");
        }
    }

    public void addSurveyOption(SurveyOption surveyOption) {
        this.surveyOption = surveyOption;
    }

    private void addTextInputForm(TextInputForm textInputForm) {
        if (textInputForm != null) {
            this.textInputForm = textInputForm;
            textInputForm.addInputForm(this);
        }
    }

    private void addChoiceInputForm(ChoiceInputForm choiceInputForm) {
        if (choiceInputForm != null) {
            this.choiceInputForm = choiceInputForm;
            choiceInputForm.addInputForm(this);
        }
    }
}
