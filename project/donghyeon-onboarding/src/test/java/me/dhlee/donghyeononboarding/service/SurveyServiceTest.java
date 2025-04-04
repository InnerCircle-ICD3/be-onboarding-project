package me.dhlee.donghyeononboarding.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.dhlee.donghyeononboarding.domain.ItemType;
import me.dhlee.donghyeononboarding.domain.Survey;
import me.dhlee.donghyeononboarding.dto.request.SurveyCreateRequest;
import me.dhlee.donghyeononboarding.dto.request.SurveyItemCreateRequest;
import me.dhlee.donghyeononboarding.dto.request.SurveyItemOptionCreateRequest;
import me.dhlee.donghyeononboarding.exception.AppException;
import me.dhlee.donghyeononboarding.exception.ErrorCode;
import me.dhlee.donghyeononboarding.repository.SurveyRepository;

@SpringBootTest
class SurveyServiceTest {

    @Autowired
    protected SurveyService surveyService;

    @Autowired
    protected SurveyRepository surveyRepository;

    @BeforeEach
    void setUp() {
        surveyRepository.deleteAllInBatch();
    }

    @DisplayName("설문조사를 생성할 수 있다.")
    @Test
    void createSurvey() {
        var option = new SurveyItemOptionCreateRequest("옵션1", 0);
        var item = new SurveyItemCreateRequest("질문1", "질문 설명1", ItemType.MULTI_SELECT, true, 0, List.of(option));
        var request = new SurveyCreateRequest("설문 제목", "설문 설명", List.of(item));

        var result = surveyService.createSurvey(request);

        assertThat(result).isNotNull();

        List<Survey> all = surveyRepository.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getTitle()).isEqualTo("설문 제목");
        assertThat(all.get(0).getDescription()).isEqualTo("설문 설명");
    }
}
