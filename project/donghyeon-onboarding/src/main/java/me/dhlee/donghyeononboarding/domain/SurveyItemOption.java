package me.dhlee.donghyeononboarding.domain;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dhlee.donghyeononboarding.common.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Table(name = "survey_item_options")
@Entity
public class SurveyItemOption extends BaseEntity {

    @JoinColumn(name = "survey_item_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private SurveyItem surveyItem;

    @Column(length = 100, nullable = false)
    private String title;

    private int displayOrder;

    @Builder
    private SurveyItemOption(String title, int displayOrder, SurveyItem surveyItem) {
        this.title = title;
        this.displayOrder = displayOrder;
        this.surveyItem = surveyItem;
    }
}
