package com.example.ranyoungonboarding.api;

import com.example.ranyoungonboarding.domain.Answer;
import com.example.ranyoungonboarding.domain.Response;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ResponseDto {
    private Long id;
    private Long surveyId;
    private List<AnswerDto> answers;
    private LocalDateTime submittedAt;

    public static ResponseDto fromEntity(Response response) {
        return ResponseDto.builder()
                .id(response.getId())
                .surveyId(response.getSurvey().getId())
                .answers(response.getAnswers().stream()
                        .map(AnswerDto::fromEntity)
                        .collect(Collectors.toList()))
                .submittedAt(response.getSubmittedAt())
                .build();
    }
}

@Getter
@Builder
class AnswerDto {
    private Long id;
    private Long questionId;
    private String questionName;
    private String textValue;
    private List<String> multipleValues;

    public static AnswerDto fromEntity(Answer answer) {
        return AnswerDto.builder()
                .id(answer.getId())
                .questionId(answer.getQuestion().getId())
                .questionName(answer.getQuestion().getName())
                .textValue(answer.getTextValue())
                .multipleValues(answer.getMultipleValues())
                .build();
    }
}