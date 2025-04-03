package com.survey.application.dto.dto;

import com.survey.domain.survey.ChoiceInputForm;
import com.survey.domain.survey.ChoiceType;
import com.survey.domain.survey.InputOption;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateChoiceInputFormDto {

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
