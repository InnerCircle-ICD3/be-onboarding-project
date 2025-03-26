package com.innercircle.yeonwoo_onboarding.service;

import com.innercircle.yeonwoo_onboarding.domain.Survey;
import com.innercircle.yeonwoo_onboarding.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SurveyServiceTest {

    @Mock
    private SurveyRepository surveyRepository;

    @InjectMocks
    private SurveyService surveyService;

    private Survey testSurvey;

    @BeforeEach
    void setUp() {
        testSurvey = new Survey();
        testSurvey.setId("test-id");
        testSurvey.setName("Test Survey");
        testSurvey.setDescription("Test Description");
    }

    @Test
    void findAllSurveys_ShouldReturnAllSurveys() {
        // Given
        List<Survey> expectedSurveys = Arrays.asList(testSurvey);
        when(surveyRepository.findAll()).thenReturn(expectedSurveys);

        // When
        List<Survey> actualSurveys = surveyService.findAllSurveys();

        // Then
        assertThat(actualSurveys).isEqualTo(expectedSurveys);
        verify(surveyRepository).findAll();
    }

    @Test
    void findSurveyById_WithValidId_ShouldReturnSurvey() {
        // Given
        when(surveyRepository.findById("test-id")).thenReturn(Optional.of(testSurvey));

        // When
        Survey foundSurvey = surveyService.findSurveyById("test-id");

        // Then
        assertThat(foundSurvey).isEqualTo(testSurvey);
        verify(surveyRepository).findById("test-id");
    }

    @Test
    void findSurveyById_WithInvalidId_ShouldThrowException() {
        // Given
        String invalidId = "invalid-id";
        when(surveyRepository.findById(invalidId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> surveyService.findSurveyById(invalidId));
        verify(surveyRepository).findById(invalidId);
    }

    @Test
    void createSurvey_ShouldReturnSavedSurvey() {
        // Given
        when(surveyRepository.save(any(Survey.class))).thenReturn(testSurvey);

        // When
        Survey createdSurvey = surveyService.createSurvey(testSurvey);

        // Then
        assertThat(createdSurvey).isEqualTo(testSurvey);
        verify(surveyRepository).save(testSurvey);
    }

    @Test
    void deleteSurvey_ShouldDeleteSurvey() {
        // Given
        String surveyId = "test-id";
        doNothing().when(surveyRepository).deleteById(surveyId);

        // When
        surveyService.deleteSurvey(surveyId);

        // Then
        verify(surveyRepository).deleteById(surveyId);
    }
}