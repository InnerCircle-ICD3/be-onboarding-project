package com.innercircle.yeonwoo_onboarding.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Survey {
    @Id
    @Column(name = "SURVEY_ID")
    private String id;

    @Column(name = "SURVEY_NM", nullable = false)
    private String name;

    @Column(name = "SURVEY_DSCRPTN", nullable = false)
    private String description;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<SurveyItem> items = new ArrayList<>();
}