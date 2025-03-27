package com.survey.application.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.survey.application.dto.dto.UpdateInputFormDto;
import com.survey.application.dto.dto.UpdateSurveyOptionDto;
import com.survey.application.dto.request.CreateSurveyRequest;
import com.survey.application.dto.request.UpdateSurveyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    @DisplayName("유효하지 않은 설문 조사 생성 요청이 전달되면 400 Bad Request와 validation error 메시지를 반환한다")
    void create_survey_validation_exception() throws Exception {
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
    @DisplayName("유효하지 않은 설문조사 수정 요청이 전달되면 400 Bad Request와 validation error 메시지를 반환한다")
    void update_survey_validation_exception() throws Exception {
        // given
        UpdateSurveyRequest invalidRequest = new UpdateSurveyRequest(
                1L,
                null,
                null,
                List.of(new UpdateSurveyOptionDto(
                        null,
                        "제목",
                        "설명",
                        true,
                        new UpdateInputFormDto(null, null, null)
                ))
        );

        // when // then
        mockMvc.perform(put("/api/survey")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("validation error"))
                .andExpect(jsonPath("$.errors.title").value("must not be null"))
                .andExpect(jsonPath("$.errors.description").value("must not be null"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("SurveyOptionDto의 유효성 검사 실패 시 적절한 오류 메시지를 반환한다")
    void validation_exception() throws Exception {
        // given
        UpdateSurveyRequest invalidOptionRequest = new UpdateSurveyRequest(
                1L,
                "유효한 제목",
                "유효한 설명",
                List.of(new UpdateSurveyOptionDto(
                        null,
                        null,
                        null,
                        true,
                        new UpdateInputFormDto(
                                null,
                                null,
                                null
                        )
                ))
        );

        // when // then
        mockMvc.perform(put("/api/survey")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOptionRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("validation error"))
                .andExpect(jsonPath("$.errors['surveyOptionDtos[0].title']").exists())
                .andExpect(jsonPath("$.errors['surveyOptionDtos[0].description']").exists())
                .andExpect(jsonPath("$.errors['surveyOptionDtos[0].inputFormDto.question']").exists());
    }

}
