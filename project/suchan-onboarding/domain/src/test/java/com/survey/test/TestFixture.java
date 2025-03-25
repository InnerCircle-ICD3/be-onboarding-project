package com.survey.test;

import com.survey.domain.InputForm;
import com.survey.domain.SurveyOption;
import com.survey.domain.TextInputForm;
import com.survey.domain.TextType;

public class TestFixture {

    public static SurveyOption createSurveyOption() {
        InputForm inputForm = new InputForm("질문1", new TextInputForm(TextType.LONG));
        return new SurveyOption("설문 받을 항목", "설문 받을 항목 설명", true, inputForm);
    }
}
