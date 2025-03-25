package com.survey.application.request;

import com.survey.application.dto.SurveyOptionDto;
import com.survey.domain.Survey;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 변경/추가/삭제 모두 처리하는 요청 dto
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSurveyRequest {
    @NotNull
    private Long surveyId;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private List<SurveyOptionDto> surveyOptionDtos;

    public Survey create() {
        return new Survey(
                surveyId,
                title,
                description,
                surveyOptionDtos.stream()
                        .map(SurveyOptionDto::create)
                        .toList()
        );
    }
}
