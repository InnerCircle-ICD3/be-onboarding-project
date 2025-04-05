package com.survey.application.service;

import com.survey.application.dto.request.CreateSurveyRequest;
import com.survey.application.dto.request.UpdateSurveyRequest;
import com.survey.application.dto.response.GetAllSurveyResultResponse;
import com.survey.application.dto.response.GetAllSurveyResultResponse.InputFormResultDto;
import com.survey.application.dto.response.GetAllSurveyResultResponse.SurveyOptionResultDto;
import com.survey.application.dto.response.GetAllSurveyResultResponse.TextResultDto;
import com.survey.application.test.TestFixture;
import com.survey.application.test.TestSurveyResultResponseComparator;
import com.survey.domain.survey.Survey;
import com.survey.domain.survey.SurveyOption;
import com.survey.domain.surveyResponse.SurveyOptionResponse;
import com.survey.domain.surveyResponse.SurveyResponse;
import com.survey.domain.surveyResponse.repository.SurveyResponseRepository;
import com.survey.domain.surveyResponse.service.SurveyResponseValidationService;
import com.survey.test.FakeSurveyRepository;
import com.survey.test.TestSurveyEntityComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SurveyServiceTest {

    private SurveyService surveyService;
    private FakeSurveyRepository surveyRepository;
    private TestSurveyEntityComparator comparator;
    private SurveyResponseRepository surveyResponseRepository;

    @BeforeEach
    void setUp() {
        surveyRepository = new FakeSurveyRepository(new HashMap<>());
        surveyResponseRepository = new FakeSurveyResponseRepository(new HashMap<>());
        SurveyResponseValidationService surveyResponseValidationService = new SurveyResponseValidationService(surveyRepository);
        SurveyResponseMapperService surveyResponseMapperService = new SurveyResponseMapperService();
        surveyService = new SurveyService(surveyRepository, surveyResponseValidationService, surveyResponseRepository, surveyResponseMapperService);
        comparator = new TestSurveyEntityComparator();
    }

    @Nested
    @DisplayName("설문조사 생성 테스트")
    class CreateSurveyTest {
        @Test
        @DisplayName("설문조사를 요청 본문으로부터 생성하고 저장할 수 있다")
        void create() {
            // given
            CreateSurveyRequest request = TestFixture.createDefaultSurveyRequest();

            // when
            Long surveyId = surveyService.registerSurvey(request);

            // then
            assertThat(surveyRepository.getCallCounter()).isEqualTo(1);

            Survey survey = surveyRepository.findById(surveyId).orElseThrow(IllegalArgumentException::new);
            assertThat(survey.getTitle()).isEqualTo("만족도 설문조사");
            assertThat(survey.getSurveyOptions()).hasSize(2);
        }
    }

    @Nested
    @DisplayName("설문조사 수정 테스트")
    class ModifySurveyTest {
        private Long existingSurveyId;

        @BeforeEach
        void setUp() {
            Survey existingSurvey = TestFixture.createDefaultSurveyRequest().create();
            surveyRepository.save(existingSurvey);
            existingSurveyId = existingSurvey.getId();
        }

        @Test
        @DisplayName("설문조사의 기본 정보를 수정할 수 있다")
        void modify_survey_basic_info() {
            // given
            UpdateSurveyRequest request = TestFixture.updateDefaultSurveyRequest(existingSurveyId);

            // when
            surveyService.changeSurvey(request);

            // then
            Survey updated = surveyRepository.findCompleteSurvey(existingSurveyId).orElseThrow(IllegalArgumentException::new);
            Survey expected = request.create();

            assertThat(updated.getTitle()).isEqualTo(expected.getTitle());
            assertThat(updated.getDescription()).isEqualTo(expected.getDescription());
        }

        @Test
        @DisplayName("설문조사의 옵션을 수정할 수 있다")
        void modify_survey_options() {
            // given
            UpdateSurveyRequest request = TestFixture.updateDefaultSurveyRequest(existingSurveyId);

            // when
            surveyService.changeSurvey(request);

            // then
            Survey updated = surveyRepository.findCompleteSurvey(existingSurveyId).orElseThrow(IllegalArgumentException::new);
            Survey expected = request.create();

            assertThat(updated.getActivatedOptionSize()).isEqualTo(expected.getActivatedOptionSize());

            List<SurveyOption> activatedUpdatedSurveyOptions = updated.getSurveyOptions().stream().filter(SurveyOption::isActivated).toList();
            List<SurveyOption> activatedExpectedSurveyOptions = expected.getSurveyOptions().stream().filter(SurveyOption::isActivated).toList();

            assertThat(comparator.assertValuesAreEqualSurveyOption(
                    activatedUpdatedSurveyOptions.getFirst(),
                    activatedExpectedSurveyOptions.getFirst()
            )).isTrue();

            assertThat(comparator.assertValuesAreEqualSurveyOption(
                    activatedUpdatedSurveyOptions.get(1),
                    activatedExpectedSurveyOptions.get(1)
            )).isTrue();
        }

        @Test
        @DisplayName("설문조사에 새 옵션을 추가할 수 있다")
        void add_new_survey_options() {
            // given
            Survey existingSurvey = surveyRepository.findCompleteSurvey(existingSurveyId).orElseThrow(IllegalArgumentException::new);
            SurveyOption firstOption = existingSurvey.getSurveyOptions().getFirst();
            SurveyOption secondOption = existingSurvey.getSurveyOptions().get(1);
            UpdateSurveyRequest request = TestFixture.updateSurveyWithAdditionalTwoSurveyOptionsRequest(existingSurveyId);

            // when
            surveyService.changeSurvey(request);

            // then
            Survey updated = surveyRepository.findCompleteSurvey(existingSurveyId).orElseThrow(IllegalArgumentException::new);
            Survey expected = request.create();

            assertThat(comparator.assertValuesAreEqualSurvey(updated, expected)).isTrue();
            assertThat(comparator.assertValuesAreEqualSurveyOption(firstOption, updated.getSurveyOptions().getFirst())).isTrue();
            assertThat(comparator.assertValuesAreEqualSurveyOption(secondOption, updated.getSurveyOptions().get(1))).isTrue();
        }

        @Test
        @DisplayName("존재하지 않는 설문조사를 수정하려 하면 예외가 발생한다")
        void modify_non_existing_survey() {
            // given
            Long nonExistingSurveyId = 9999L;
            UpdateSurveyRequest request = TestFixture.updateDefaultSurveyRequest(nonExistingSurveyId);

            // when // then
            assertThatThrownBy(() -> surveyService.changeSurvey(request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("설문 조사를 찾을 수 없습니다.");
        }
    }

    @Test
    @DisplayName("설문 조사 최종 결과를 조회할 수 있다.")
    void get_all_survey_responses() {
        // given
        Long givenSurveyId = 1L;

        SurveyOption shortOption = TestFixture.createShortTextSurveyOption(true);
        SurveyOption longOption = TestFixture.createLongTextSurveyOption(true);
        Survey survey = new Survey(givenSurveyId, "제목", "설명", List.of(shortOption, longOption));
        surveyRepository.save(survey);

        SurveyOptionResponse shortResponse = TestFixture.createShortTextSurveyOptionResponse();
        SurveyOptionResponse longResponse = TestFixture.createLongTextSurveyOptionResponse();
        SurveyResponse givenSurveyResponse = new SurveyResponse(
                givenSurveyId,
                1L,
                LocalDateTime.now(),
                List.of(shortResponse, longResponse)
        );
        surveyResponseRepository.save(givenSurveyResponse);

        // when
        List<GetAllSurveyResultResponse> results = surveyService.getAllSurveyResponses(givenSurveyId);

        // then
        assertThat(results).hasSize(1);

        GetAllSurveyResultResponse expectedResponse = new GetAllSurveyResultResponse(
                givenSurveyId,
                1L,
                "제목",
                "설명",
                List.of(
                        new SurveyOptionResultDto(
                                1L,
                                "설문 항목1",
                                "설명1",
                                true,
                                new InputFormResultDto(
                                        1L,
                                        "질문1",
                                        new TextResultDto(1L, "단답형", "short answer")
                                )
                        ),
                        new SurveyOptionResultDto(
                                2L,
                                "설문 항목2",
                                "설명2",
                                true,
                                new InputFormResultDto(
                                        2L,
                                        "질문2",
                                        new TextResultDto(2L, "장문형", "long answer")
                                )
                        )
                )
        );

        assertThat(TestSurveyResultResponseComparator.areEqual(expectedResponse, results.getFirst())).isTrue();
    }

}