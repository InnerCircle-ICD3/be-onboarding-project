package com.survey.application.dto;

import com.survey.domain.InputForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InputFormDto {

    private String question;
    private TextInputFormDto textInputFormDto;
    private ChoiceInputFormDto choiceInputFormDto;

    public InputForm create() {
        return new InputForm(
                question,
                textInputFormDto.create(),
                choiceInputFormDto.create()
        );
    }
}
