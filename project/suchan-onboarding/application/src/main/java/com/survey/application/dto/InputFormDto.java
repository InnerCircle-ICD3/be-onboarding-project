package com.survey.application.dto;

import com.survey.domain.InputForm;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InputFormDto {

    @NotNull
    private String question;
    private TextInputFormDto textInputFormDto;
    private ChoiceInputFormDto choiceInputFormDto;

    public InputForm create() {
        if (textInputFormDto != null) {
            return new InputForm(
                    question,
                    textInputFormDto.create()
            );
        }

        if (choiceInputFormDto != null) {
            return new InputForm(
                    question,
                    choiceInputFormDto.create()
            );
        }

        throw new IllegalStateException("InputFormDto 의 내용이 비어있습니다.");
    }
}
