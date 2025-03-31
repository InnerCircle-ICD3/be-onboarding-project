package com.innercircle.yeonwoo_onboarding.domain;

// Only needs InputType enum import
import com.innercircle.yeonwoo_onboarding.domain.enums.InputType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class SurveyItem {
    @Id
    @Column(name = "SURVEY_ITEM_ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SURVEY_ID")
    private Survey survey;

    @Column(name = "SURVEY_ITEM_NM", nullable = false)
    private String name;

    @Column(name = "SURVEY_ITEM_DSCRPTN", nullable = false)
    private String description;

    @Column(name = "INPUT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private InputType inputType;

    @Column(name = "REQUIRED_YN", nullable = false)
    private char requiredYn;

    @OneToMany(mappedBy = "surveyItem", cascade = CascadeType.ALL)
    private List<SurveyItemOption> options = new ArrayList<>();
}