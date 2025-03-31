package com.innercircle.yeonwoo_onboarding.service;

import com.innercircle.yeonwoo_onboarding.domain.Survey;
import com.innercircle.yeonwoo_onboarding.repository.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Should return all surveys when findAllSurveys is called")
    void findAllSurveys_ShouldReturnAllSurveys() {
        // Given
        List<Survey> expectedSurveys = Arrays.asList(testSurvey);
        when(surveyRepository.findAll()).thenReturn(expectedSurveys);

        // When
        List<Survey> actualSurveys = surveyService.findAllSurveys();

        // Then
        assertThat(actualSurveys).isNotNull();
        assertThat(actualSurveys).hasSize(1);
        assertThat(actualSurveys.get(0)).isEqualTo(testSurvey);
        verify(surveyRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return survey when valid ID is provided")
    void findSurveyById_WithValidId_ShouldReturnSurvey() {
        // Given
        String surveyId = "test-id";
        when(surveyRepository.findById(surveyId)).thenReturn(Optional.of(testSurvey));

        // When
        Survey foundSurvey = surveyService.findSurveyById(surveyId);

        // Then
        assertThat(foundSurvey).isNotNull();
        assertThat(foundSurvey.getId()).isEqualTo(surveyId);
        assertThat(foundSurvey.getName()).isEqualTo("Test Survey");
        verify(surveyRepository, times(1)).findById(surveyId);
    }

    @Test
    @DisplayName("Should throw exception when survey is not found")
    void findSurveyById_WithInvalidId_ShouldThrowException() {
        // Given
        String invalidId = "invalid-id";
        when(surveyRepository.findById(invalidId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> surveyService.findSurveyById(invalidId));
        
        assertThat(exception).hasMessageContaining("Survey not found");
        verify(surveyRepository, times(1)).findById(invalidId);
    }

    @Test
    @DisplayName("Should successfully create and return new survey")
    void createSurvey_ShouldReturnSavedSurvey() {
        // Given
        Survey newSurvey = new Survey();
        newSurvey.setName("New Survey");
        newSurvey.setDescription("New Description");
        
        when(surveyRepository.save(any(Survey.class))).thenReturn(newSurvey);

        // When
        Survey createdSurvey = surveyService.createSurvey(newSurvey);

        // Then
        assertThat(createdSurvey).isNotNull();
        assertThat(createdSurvey.getName()).isEqualTo("New Survey");
        assertThat(createdSurvey.getDescription()).isEqualTo("New Description");
        verify(surveyRepository, times(1)).save(any(Survey.class));
    }

    @Test
    @DisplayName("Should successfully update existing survey")
    void updateSurvey_WithValidId_ShouldUpdateAndReturnSurvey() {
        // Given
        String surveyId = "test-id";
        Survey updatedSurvey = new Survey();
        updatedSurvey.setName("Updated Survey");
        updatedSurvey.setDescription("Updated Description");
        
        when(surveyRepository.findById(surveyId)).thenReturn(Optional.of(testSurvey));
        when(surveyRepository.save(any(Survey.class))).thenReturn(updatedSurvey);

        // When
        Survey result = surveyService.updateSurvey(surveyId, updatedSurvey);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Survey");
        assertThat(result.getDescription()).isEqualTo("Updated Description");
        verify(surveyRepository).findById(surveyId);
        verify(surveyRepository).save(any(Survey.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent survey")
    void updateSurvey_WithInvalidId_ShouldThrowException() {
        // Given
        String invalidId = "invalid-id";
        Survey updatedSurvey = new Survey();
        when(surveyRepository.findById(invalidId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> surveyService.updateSurvey(invalidId, updatedSurvey));
        
        assertThat(exception)
            .hasMessageContaining("Survey not found")
            .hasMessageContaining(invalidId);
        verify(surveyRepository).findById(invalidId);
        verify(surveyRepository, never()).save(any(Survey.class));
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent survey")
    void deleteSurvey_ShouldDeleteSurvey() {
        // Given
        String surveyId = "test-id";
        when(surveyRepository.findById(surveyId)).thenReturn(Optional.of(testSurvey));

        // When
        surveyService.deleteSurvey(surveyId);

        // Then
        verify(surveyRepository).findById(surveyId);
        verify(surveyRepository).deleteById(surveyId);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent survey")
    void deleteSurvey_WithInvalidId_ShouldThrowException() {
        // Given
        String invalidId = "invalid-id";
        when(surveyRepository.findById(invalidId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> surveyService.deleteSurvey(invalidId));
        
        assertThat(exception)
            .hasMessageContaining("Survey not found")
            .hasMessageContaining(invalidId);
        verify(surveyRepository).findById(invalidId);
        verify(surveyRepository, never()).deleteById(any());
    }
}