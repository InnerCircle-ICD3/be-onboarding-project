package com.survey.domain;

import jakarta.persistence.*;

@Entity
public class TextInputForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TextType textType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "input_form_id")
    private InputForm inputForm;
}
