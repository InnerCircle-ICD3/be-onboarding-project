package com.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChoiceInputForm {

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
        this.choiceType = choiceType;
        this.inputOptions = inputOptions;
    }

    public void addInputForm(InputForm inputForm) {
        this.inputForm = inputForm;
    }
}