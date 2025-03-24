package com.survey.domain;

import java.util.List;

public class TestFixture {

    public static SurveyOption createSurveyOption() {
        InputForm inputForm = new InputForm("질문1", new TextInputForm(TextType.LONG));
        return new SurveyOption("설문 받을 항목", "설문 받을 항목 설명", true, List.of(inputForm));
    }
}
