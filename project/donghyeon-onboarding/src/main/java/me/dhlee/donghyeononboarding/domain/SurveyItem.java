package me.dhlee.donghyeononboarding.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.SQLRestriction;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dhlee.donghyeononboarding.common.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Table(name = "survey_items")
@Entity
public class SurveyItem extends BaseEntity {

    @JoinColumn(name = "survey_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Survey survey;

    @OneToMany(mappedBy = "surveyItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SurveyItemOption> options = new HashSet<>();

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemType itemType;

    @Column(nullable = false)
    private boolean isRequired = false;

    private int displayOrder;

    @Builder
    public SurveyItem(
        String title,
        String description,
        ItemType itemType,
        boolean isRequired,
        int displayOrder,
        Survey survey
    ) {
        this.title = title;
        this.description = description;
        this.itemType = itemType;
        this.isRequired = isRequired;
        this.displayOrder = displayOrder;
        this.survey = survey;
    }
}