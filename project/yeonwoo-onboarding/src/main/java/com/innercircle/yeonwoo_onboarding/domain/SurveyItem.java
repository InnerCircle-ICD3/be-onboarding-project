package com.innercircle.yeonwoo_onboarding.domain;

// Only needs InputType enum import
import com.innercircle.yeonwoo_onboarding.domain.enums.InputType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SurveyItem {
    @Id
    @GeneratedValue(generator = "survey-item-id-generator")
    @GenericGenerator(name = "survey-item-id-generator", 
                     strategy = "com.innercircle.yeonwoo_onboarding.domain.generator.SurveyItemIdGenerator")
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

    public void setId(String id) {  
        this.id = id;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType;
    }

    public void setRequired(boolean required) {
        this.requiredYn = required ? 'Y' : 'N';
    }

    public boolean isRequired() {
        return this.requiredYn == 'Y';
    }

    public void addOption(SurveyItemOption option) {
        this.options.add(option);
        option.setSurveyItem(this);
    }
}