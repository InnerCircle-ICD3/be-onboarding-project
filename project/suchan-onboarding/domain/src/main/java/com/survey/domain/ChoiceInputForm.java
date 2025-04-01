package com.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChoiceInputForm {
    private static final String INPUT_OPTIONS_EXCEPTION_MESSAGE = "선택 항목은 1개 이상이어야 합니다.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChoiceType choiceType;

    @ElementCollection
    @CollectionTable(
            name = "choice_input_options",
            joinColumns = @JoinColumn(name = "choice_input_form_id")
    )
    @Column(nullable = false)
    private List<InputOption> inputOptions = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "input_form_id")
    private InputForm inputForm;

    public ChoiceInputForm(ChoiceType choiceType, List<InputOption> inputOptions) {
        validateInputOptions(inputOptions);
        this.choiceType = choiceType;
        this.inputOptions = inputOptions;
    }

    private void validateInputOptions(List<InputOption> inputOptions) {
        if (inputOptions == null || inputOptions.isEmpty()) {
            throw new IllegalArgumentException(INPUT_OPTIONS_EXCEPTION_MESSAGE);
        }
    }

    public void addInputForm(InputForm inputForm) {
        this.inputForm = inputForm;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ChoiceInputForm that = (ChoiceInputForm) o;
        return Objects.equals(id, that.id) && choiceType == that.choiceType && Objects.equals(inputOptions, that.inputOptions) && Objects.equals(inputForm, that.inputForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, choiceType, inputOptions, inputForm);
    }
}