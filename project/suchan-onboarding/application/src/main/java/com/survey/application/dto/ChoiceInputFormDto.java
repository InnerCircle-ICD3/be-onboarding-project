package com.survey.application.dto;

import com.survey.domain.ChoiceInputForm;
import com.survey.domain.ChoiceType;
import com.survey.domain.InputOption;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceInputFormDto {

    @NotNull
    private String choiceType;

    @NotEmpty
    private List<String> inputOptions;

    public ChoiceInputForm create() {
        return new ChoiceInputForm(
                ChoiceType.findByName(choiceType),
                inputOptions.stream()
                        .map(InputOption::new)
                        .toList()
        );
    }
}
