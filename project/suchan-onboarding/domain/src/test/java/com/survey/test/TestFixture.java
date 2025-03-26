package com.survey.test;

import com.survey.domain.*;

import java.util.List;

public class TestFixture {

    public static SurveyOption createSurveyOption() {
        InputForm inputForm = new InputForm("질문1", new TextInputForm(TextType.LONG));
        return new SurveyOption("설문 받을 항목", "설문 받을 항목 설명", true, inputForm);
    }

    public static SurveyOption createDiffLongTextTypeSurveyOption() {
        InputForm inputForm = new InputForm("새로운 질문", new TextInputForm(TextType.LONG));
        return new SurveyOption("새로운 설문 받을 항목", "새로운 설문 받을 항목 설명", false, inputForm);
    }

    public static SurveyOption createDiffSingleChoiceTypeSurveyOption() {
        InputForm inputForm = new InputForm(
                "새로운 질문",
                new ChoiceInputForm(
                        ChoiceType.SINGLE,
                        List.of(new InputOption("선택 항목1"), new InputOption("선택 항목2")
                        )
                ));
        return new SurveyOption("새로운 설문 받을 항목", "새로운 설문 받을 항목 설명", false, inputForm);
    }
}
