package com.survey.application.service;

import com.survey.application.dto.request.CreateSurveyRequest;
import com.survey.application.dto.request.UpdateSurveyRequest;
import com.survey.application.repository.FakeSurveyRepository;
import com.survey.application.test.TestFixture;
import com.survey.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SurveyServiceTest {

    private SurveyService surveyService;
    private FakeSurveyRepository surveyRepository;

    @BeforeEach
    void setUp() {
        surveyRepository = new FakeSurveyRepository(new HashMap<>());
        surveyService = new SurveyService(surveyRepository);
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
            Survey updated = surveyRepository.findCompleteSurveyFetchJoin(existingSurveyId).orElseThrow(IllegalArgumentException::new);
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
            Survey updated = surveyRepository.findCompleteSurveyFetchJoin(existingSurveyId).orElseThrow(IllegalArgumentException::new);
            Survey expected = request.create();

            assertThat(updated.getSurveyOptions()).hasSameSizeAs(expected.getSurveyOptions());

            assertSurveyOptionEquals(
                    updated.getSurveyOptions().getFirst(),
                    expected.getSurveyOptions().getFirst()
            );

            assertSurveyOptionEquals(
                    updated.getSurveyOptions().get(1),
                    expected.getSurveyOptions().get(1)
            );
        }

        @Test
        @DisplayName("설문조사에 새 옵션을 추가할 수 있다")
        void add_new_survey_options() {
            // given
            UpdateSurveyRequest request = TestFixture.updateDefaultSurveyRequest(existingSurveyId);

            // when
            surveyService.changeSurvey(request);

            // then
            Survey updated = surveyRepository.findCompleteSurveyFetchJoin(existingSurveyId).orElseThrow(IllegalArgumentException::new);
            Survey expected = request.create();

            InputForm thirdInputForm = expected.getSurveyOptions().get(2).getInputForm();
            assertThat(thirdInputForm.getTextInputForm().getTextType()).isEqualTo(TextType.LONG);

            InputForm fourthInputForm = expected.getSurveyOptions().get(3).getInputForm();
            assertThat(fourthInputForm.getChoiceInputForm().getChoiceType()).isEqualTo(ChoiceType.SINGLE);

            List<String> expectedOptions = List.of("전혀 없음", "별로 없음", "보통", "약간 있음", "매우 있음");
            for (int i = 0; i < 5; i++) {
                assertThat(fourthInputForm.getChoiceInputForm().getInputOptions().get(i).getOption())
                        .isEqualTo(expectedOptions.get(i));
            }
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

    private void assertSurveyOptionEquals(SurveyOption actual, SurveyOption expected) {
        assertThat(actual.getTitle()).isEqualTo(expected.getTitle());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());

        InputForm actualInputForm = actual.getInputForm();
        InputForm expectedInputForm = expected.getInputForm();

        if (actualInputForm.hasChoiceInputForm() && expectedInputForm.hasChoiceInputForm()) {
            assertThat(actualInputForm.getChoiceInputForm().getId())
                    .isEqualTo(expectedInputForm.getChoiceInputForm().getId());
            assertThat(actualInputForm.getChoiceInputForm().getChoiceType())
                    .isEqualTo(expectedInputForm.getChoiceInputForm().getChoiceType());

            int optionCnt = actualInputForm.getChoiceInputForm().getInputOptions().size();
            for (int i = 0; i < optionCnt; i++) {
                assertThat(actualInputForm.getChoiceInputForm().getInputOptions().get(i).getOption())
                        .isEqualTo(expectedInputForm.getChoiceInputForm().getInputOptions().get(i).getOption());
            }
        } else if (actualInputForm.hasTextInputForm() && expectedInputForm.hasTextInputForm()) {
            assertThat(actualInputForm.getTextInputForm().getTextType())
                    .isEqualTo(expectedInputForm.getTextInputForm().getTextType());
        }
    }
}