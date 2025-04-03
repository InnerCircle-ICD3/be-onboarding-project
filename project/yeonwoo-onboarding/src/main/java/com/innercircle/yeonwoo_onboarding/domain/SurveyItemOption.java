package com.innercircle.yeonwoo_onboarding.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@NoArgsConstructor
public class SurveyItemOption {
    @Id
    @GeneratedValue(generator = "survey-item-option-id-generator")
    @GenericGenerator(name = "survey-item-option-id-generator", 
                     strategy = "com.innercircle.yeonwoo_onboarding.domain.generator.SurveyItemOptionIdGenerator")
    @Column(name = "SURVEY_ITEM_OPTION_ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SURVEY_ITEM_ID")
    private SurveyItem surveyItem;

    @Column(name = "OPTION_TXT", nullable = false)
    private String optionText;

    public void setId(String id) {  // Changed parameter type to String
        this.id = id;
    }

    public void setSurveyItem(SurveyItem surveyItem) {
        this.surveyItem = surveyItem;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }
}