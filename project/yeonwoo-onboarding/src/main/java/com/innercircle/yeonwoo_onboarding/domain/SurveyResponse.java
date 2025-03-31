package com.innercircle.yeonwoo_onboarding.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class SurveyResponse {
    @Id
    @Column(name = "SURVEY_RESPONSE_ID")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SURVEY_ID")
    private Survey survey;

    @Column(name = "RESPONDENT_ID")
    private String respondentId;

    @OneToMany(mappedBy = "surveyResponse", cascade = CascadeType.ALL)
    private List<SurveyItemResponse> itemResponses = new ArrayList<>();
}