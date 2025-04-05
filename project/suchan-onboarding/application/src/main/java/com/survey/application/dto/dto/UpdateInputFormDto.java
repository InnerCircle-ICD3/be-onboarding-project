package com.survey.application.dto.dto;

import com.survey.domain.survey.InputForm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInputFormDto {
    private static final String INPUT_FORM_DTO_EXCEPTION_MESSAGE = "InputFormDto 의 내용이 비어있습니다.";

    @NotNull
    private String question;

    @Valid
    private UpdateTextInputFormDto textInputFormDto;

    @Valid
    private UpdateChoiceInputFormDto choiceInputFormDto;

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

        throw new IllegalStateException(INPUT_FORM_DTO_EXCEPTION_MESSAGE);
    }
}
