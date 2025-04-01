package com.example.ranyoungonboarding.api;

import com.example.ranyoungonboarding.domain.Question;
import com.example.ranyoungonboarding.domain.Survey;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class SurveyResponse {
    private Long id;
    private String name;
    private String description;
    private List<QuestionResponse> questions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static SurveyResponse fromEntity(Survey survey) {
        return SurveyResponse.builder()
                .id(survey.getId())
                .name(survey.getName())
                .description(survey.getDescription())
                .questions(survey.getQuestions().stream()
                        .map(QuestionResponse::fromEntity)
                        .collect(Collectors.toList()))
                .createdAt(survey.getCreatedAt())
                .updatedAt(survey.getUpdatedAt())
                .build();
    }
}

@Getter
@Builder
class QuestionResponse {
    private Long id;
    private String name;
    private String description;
    private String type;
    private Boolean required;
    private List<String> options;
    private Integer position;

    public static QuestionResponse fromEntity(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .name(question.getName())
                .description(question.getDescription())
                .type(question.getType().name())
                .required(question.getRequired())
                .options(question.getOptions())
                .position(question.getPosition())
                .build();
    }

}