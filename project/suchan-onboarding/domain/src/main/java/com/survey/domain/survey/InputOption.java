package com.survey.domain.survey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class InputOption {

    @Column(nullable = false)
    private String option;

    public InputOption(String option) {
        this.option = option;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InputOption that = (InputOption) o;
        return Objects.equals(option, that.option);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(option);
    }
}
