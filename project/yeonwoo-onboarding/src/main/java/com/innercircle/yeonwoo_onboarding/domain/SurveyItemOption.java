package com.innercircle.yeonwoo_onboarding.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class SurveyItemOption {
    @Id
    @Column(name = "SURVEY_ITEM_OPTION_ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SURVEY_ITEM_ID")
    private SurveyItem surveyItem;

    @Column(name = "OPTION_TXT", nullable = false)
    private String optionText;
}