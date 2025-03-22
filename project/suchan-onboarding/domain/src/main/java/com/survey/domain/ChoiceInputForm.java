package com.survey.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
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
}