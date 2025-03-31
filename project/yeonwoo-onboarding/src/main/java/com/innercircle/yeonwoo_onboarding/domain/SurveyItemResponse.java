package com.innercircle.yeonwoo_onboarding.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class SurveyItemResponse {
    @Id
    @Column(name = "SURVEY_ITEM_RESPONSE_ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SURVEY_RESPONSE_ID")
    private SurveyResponse surveyResponse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SURVEY_ITEM_ID")
    private SurveyItem surveyItem;

    @Column(name = "RESPONSE_TEXT")
    private String responseText;

    @ManyToMany
    @JoinTable(
        name = "SURVEY_ITEM_RESPONSE_OPTION",
        joinColumns = @JoinColumn(name = "SURVEY_ITEM_RESPONSE_ID"),
        inverseJoinColumns = @JoinColumn(name = "SURVEY_ITEM_OPTION_ID")
    )
    private Set<SurveyItemOption> selectedOptions = new HashSet<>();
}