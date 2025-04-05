package com.survey.application.dto.request;

import com.survey.application.dto.dto.UpdateSurveyOptionDto;
import com.survey.domain.survey.Survey;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
    @NotEmpty
    @Valid
    private List<UpdateSurveyOptionDto> surveyOptionDtos;

    public Survey create() {
        return new Survey(
                surveyId,
                title,
                description,
                surveyOptionDtos.stream()
                        .map(UpdateSurveyOptionDto::create)
                        .toList()
        );
    }
}
