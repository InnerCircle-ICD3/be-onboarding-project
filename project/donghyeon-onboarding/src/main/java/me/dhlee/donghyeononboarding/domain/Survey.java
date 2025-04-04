package me.dhlee.donghyeononboarding.domain;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dhlee.donghyeononboarding.common.BaseEntity;
import me.dhlee.donghyeononboarding.exception.AppException;
import me.dhlee.donghyeononboarding.exception.ErrorCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction("deleted_at is null")
@Table(name = "Surveys")
@Entity
public class Survey extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SurveyItem> items = new HashSet<>();

    @Builder
    private Survey(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void addItem(SurveyItem item) {
        if (isFull()) {
            throw new AppException(ErrorCode.SURVEY_ITEM_SIZE_OVERFLOW);
        }
        this.items.add(item);
    }

    private boolean isFull() {
        return items.size() >= 10;
    }
}
