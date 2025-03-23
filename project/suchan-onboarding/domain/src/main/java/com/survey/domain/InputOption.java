package com.survey.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class InputOption {

    @Column(nullable = false)
    private String option;

    public InputOption(String option) {
        this.option = option;
    }
}
