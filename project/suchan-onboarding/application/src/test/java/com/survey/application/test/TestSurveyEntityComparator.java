package com.survey.application.test;

import com.survey.domain.*;

import java.util.List;
import java.util.Objects;

public class TestSurveyEntityComparator {

    public boolean assertValuesAreEqualChoiceInputForm(ChoiceInputForm actual, ChoiceInputForm expected) {
        if (actual == expected) return true;
        if (actual == null || expected == null) return false;

        if (actual.getChoiceType() != expected.getChoiceType()) return false;

        List<InputOption> actualOptions = actual.getInputOptions();
        List<InputOption> expectedOptions = expected.getInputOptions();

        if (actualOptions.size() != expectedOptions.size()) return false;

        for (int i = 0; i < actualOptions.size(); i++) {
            InputOption actualOption = actualOptions.get(i);
            InputOption expectedOption = expectedOptions.get(i);

            if (!Objects.equals(actualOption.getOption(), expectedOption.getOption())) {
                return false;
            }
        }

        return true;
    }

    public boolean assertValuesAreEqualTextInputForm(TextInputForm actual, TextInputForm expected) {
        if (actual == expected) return true;
        if (actual == null || expected == null) return false;

        return actual.getTextType() == expected.getTextType();
    }

    public boolean assertValuesAreEqualInputForm(InputForm actual, InputForm expected) {
        if (actual == expected) return true;
        if (actual == null || expected == null) return false;

        if (!Objects.equals(actual.getQuestion(), expected.getQuestion())) return false;

        if (actual.hasTextInputForm() != expected.hasTextInputForm()) return false;
        if (actual.hasTextInputForm() && expected.hasTextInputForm()) {
            if (!assertValuesAreEqualTextInputForm(actual.getTextInputForm(), expected.getTextInputForm())) {
                return false;
            }
        }

        if (actual.hasChoiceInputForm() != expected.hasChoiceInputForm()) return false;
        if (actual.hasChoiceInputForm() && expected.hasChoiceInputForm()) {
            return assertValuesAreEqualChoiceInputForm(actual.getChoiceInputForm(), expected.getChoiceInputForm());
        }

        return true;
    }

    public boolean assertValuesAreEqualSurveyOption(SurveyOption actual, SurveyOption expected) {
        if (actual == expected) return true;
        if (actual == null || expected == null) return false;

        if (!Objects.equals(actual.getTitle(), expected.getTitle())) return false;
        if (!Objects.equals(actual.getDescription(), expected.getDescription())) return false;
        if (actual.isNecessary() != expected.isNecessary()) return false;

        return assertValuesAreEqualInputForm(actual.getInputForm(), expected.getInputForm());
    }

    public boolean assertValuesAreEqualSurvey(Survey actual, Survey expected) {
        if (actual == expected) return true;
        if (actual == null || expected == null) return false;

        if (!Objects.equals(actual.getTitle(), expected.getTitle())) return false;
        if (!Objects.equals(actual.getDescription(), expected.getDescription())) return false;

        List<SurveyOption> actualOptions = actual.getSurveyOptions();
        List<SurveyOption> expectedOptions = expected.getSurveyOptions();

        if (actualOptions.size() != expectedOptions.size()) return false;

        for (int i = 0; i < actualOptions.size(); i++) {
            SurveyOption actualOption = actualOptions.get(i);
            SurveyOption expectedOption = expectedOptions.get(i);

            if (!assertValuesAreEqualSurveyOption(actualOption, expectedOption)) {
                return false;
            }
        }

        return true;
    }
}