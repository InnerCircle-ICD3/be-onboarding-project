package com.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public TextInputForm(TextType textType) {
        this.textType = textType;
    }

    public void addInputForm(InputForm inputForm) {
        this.inputForm = inputForm;
    }
}
