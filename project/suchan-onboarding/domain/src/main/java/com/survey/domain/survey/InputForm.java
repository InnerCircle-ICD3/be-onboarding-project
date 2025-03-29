package com.survey.domain.survey;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class InputForm {
    private static final String NON_INPUT_EXCEPTION_MESSAGE = "최소 하나의 입력 형태는 존재해야 합니다.";
    private static final String DOUBLE_INPUT_EXCEPTION_MESSAGE = "입력 형태가 2가지일 수는 없습니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_option_id")
    private SurveyOption surveyOption;

    @OneToOne(mappedBy = "inputForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private TextInputForm textInputForm;

    @OneToOne(mappedBy = "inputForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private ChoiceInputForm choiceInputForm;

    protected InputForm(String question, TextInputForm textInputForm, ChoiceInputForm choiceInputForm) {
        validateInputForm(textInputForm, choiceInputForm);
        this.question = question;
        addTextInputForm(textInputForm);
        addChoiceInputForm(choiceInputForm);
    }

    protected InputForm(String question) {
        this(question, null, null);
    }

    public InputForm(String question, TextInputForm textInputForm) {
        this(question, textInputForm, null);
    }

    public InputForm(String question, ChoiceInputForm choiceInputForm) {
        this(question, null, choiceInputForm);
    }

    private void validateInputForm(TextInputForm textInputForm, ChoiceInputForm choiceInputForm) {
        if (textInputForm == null && choiceInputForm == null) {
            throw new IllegalArgumentException(NON_INPUT_EXCEPTION_MESSAGE);
        }

        if (textInputForm != null && choiceInputForm != null) {
            throw new IllegalArgumentException(DOUBLE_INPUT_EXCEPTION_MESSAGE);
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

    public boolean hasChoiceInputForm() {
        return this.choiceInputForm != null;
    }

    public boolean hasTextInputForm() {
        return this.textInputForm != null;
    }

    public boolean isNeededModify(InputForm newInputForm) {
        if (newInputForm.hasTextInputForm() && hasTextInputForm()) {
            return !this.textInputForm.isSameForm(newInputForm.getTextInputForm());
        } else if (newInputForm.hasChoiceInputForm() && hasChoiceInputForm()) {
            return !this.choiceInputForm.isSameForm(newInputForm.getChoiceInputForm());
        } else {
            return true;
        }
    }

    public void modifyInputForm(InputForm newInputForm) {
        this.question = newInputForm.getQuestion();
        if (newInputForm.hasTextInputForm() && hasTextInputForm()) {
            updateTextInputFormIfNeeded(newInputForm);
        } else if (newInputForm.hasChoiceInputForm() && hasChoiceInputForm()) {
            updateChoiceInputFormIfNeeded(newInputForm);
        } else if (newInputForm.hasTextInputForm() && hasChoiceInputForm()) {
            replaceChoiceWithTextInputForm(newInputForm);
        } else if (newInputForm.hasChoiceInputForm() && hasTextInputForm()) {
            replaceTextWithChoiceInputForm(newInputForm);
        }
    }

    private void updateTextInputFormIfNeeded(InputForm newInputForm) {
        if (!this.textInputForm.isSameForm(newInputForm.getTextInputForm())) {
            this.textInputForm.modify(newInputForm.getTextInputForm());
        }
    }

    private void updateChoiceInputFormIfNeeded(InputForm newInputForm) {
        if (!this.choiceInputForm.isSameForm(newInputForm.getChoiceInputForm())) {
            this.choiceInputForm.modify(newInputForm.getChoiceInputForm());
        }
    }

    private void replaceChoiceWithTextInputForm(InputForm newInputForm) {
        clearChoiceInputRelation();
        addTextInputForm(newInputForm.getTextInputForm());
    }

    private void replaceTextWithChoiceInputForm(InputForm newInputForm) {
        clearTextInputRelation();
        addChoiceInputForm(newInputForm.getChoiceInputForm());
    }

    private void clearTextInputRelation() {
        if (this.textInputForm != null) {
            this.textInputForm.addInputForm(null);
            this.textInputForm = null;
        }
    }

    private void clearChoiceInputRelation() {
        if (this.choiceInputForm != null) {
            this.choiceInputForm.addInputForm(null);
            this.choiceInputForm = null;
        }
    }

}
