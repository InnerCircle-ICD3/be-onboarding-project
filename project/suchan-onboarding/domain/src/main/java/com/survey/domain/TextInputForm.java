package com.survey.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TextInputForm that = (TextInputForm) o;
        return Objects.equals(id, that.id) && textType == that.textType && Objects.equals(inputForm, that.inputForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, textType, inputForm);
    }
}
