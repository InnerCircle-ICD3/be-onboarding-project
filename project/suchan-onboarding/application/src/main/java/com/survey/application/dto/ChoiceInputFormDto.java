package com.survey.application.dto;

import com.survey.domain.ChoiceInputForm;
import com.survey.domain.ChoiceType;
import com.survey.domain.InputOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceInputFormDto {

    private String choiceType;
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
