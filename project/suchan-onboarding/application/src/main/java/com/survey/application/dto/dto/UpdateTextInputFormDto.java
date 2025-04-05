package com.survey.application.dto.dto;

import com.survey.domain.TextInputForm;
import com.survey.domain.TextType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTextInputFormDto {
    @NotNull
    private String textType;

    public TextInputForm create() {
        return new TextInputForm(TextType.findByName(textType));
    }
}
