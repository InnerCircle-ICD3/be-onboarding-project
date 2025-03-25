package com.survey.application.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.survey.application.request.CreateSurveyRequest;
import com.survey.application.dto.InputFormDto;
import com.survey.application.dto.SurveyOptionDto;
import com.survey.application.dto.TextInputFormDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("유효하지 않은 요청이 전달되면 400 Bad Request와 validation error 메시지를 반환한다")
    void validation_exception() throws Exception {
        // given
        CreateSurveyRequest invalidRequest = new CreateSurveyRequest(
                null,
                "설명",
                Collections.emptyList()
        );

        // when // then
        mockMvc.perform(post("/api/survey")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("validation error"))
                .andExpect(jsonPath("$.errors.title").exists())
                .andExpect(jsonPath("$.errors.surveyOptionDtos").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("도메인 규칙을 위반하는 요청은 IllegalArgumentException이 발생하고 400 Bad Request를 반환한다")
    void illegal_argument_exception() throws Exception {
        // given
        List<SurveyOptionDto> tooManySurveyOptions = createInvalidSurveyOptions();

        CreateSurveyRequest invalidRequest = new CreateSurveyRequest(
                "타이틀",
                "설명",
                tooManySurveyOptions
        );

        // when // then
        mockMvc.perform(post("/api/survey")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("설문 받을 항목은 1개 ~ 10개 사이여야 합니다."))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    private List<SurveyOptionDto> createInvalidSurveyOptions() {
        List<SurveyOptionDto> options = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            InputFormDto inputFormDto = new InputFormDto(
                    "질문 " + i,
                    new TextInputFormDto("단답형"),
                    null
            );

            options.add(new SurveyOptionDto(
                    "옵션 " + i,
                    "설명 " + i,
                    true,
                    List.of(inputFormDto)
            ));
        }
        return options;
    }
}
