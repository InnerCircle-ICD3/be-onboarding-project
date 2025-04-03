package com.innercircle.yeonwoo_onboarding.service;

import com.innercircle.yeonwoo_onboarding.domain.Survey;
import com.innercircle.yeonwoo_onboarding.domain.SurveyItem;
import com.innercircle.yeonwoo_onboarding.domain.SurveyItemOption;
import com.innercircle.yeonwoo_onboarding.repository.SurveyRepository;
import com.innercircle.yeonwoo_onboarding.dto.SurveyCreateDto;
import com.innercircle.yeonwoo_onboarding.dto.SurveyItemCreateDto;
import com.innercircle.yeonwoo_onboarding.domain.enums.InputType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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

    @Mock
    private SurveyItemService surveyItemService;
    
    @Mock
    private SurveyItemOptionService surveyItemOptionService;

    @InjectMocks
    private SurveyService surveyService;

    private Survey testSurvey;

    @BeforeEach
    void setUp() {
        testSurvey = new Survey();
        testSurvey.setId("2025040300001");   
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
        String surveyId = "2025040300001";  // Changed from Long to String
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
        String invalidId = "2025040399999";  // Changed from 999L to String
        when(surveyRepository.findById(invalidId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> surveyService.findSurveyById(invalidId));
        
        assertThat(exception).hasMessageContaining("Survey not found");
        verify(surveyRepository, times(1)).findById(invalidId);
    }

    @Test
    @DisplayName("Should successfully create survey with items")
    void createSurvey_WithItems_ShouldReturnSavedSurvey() {
        // Given
        SurveyCreateDto surveyDto = new SurveyCreateDto();
        surveyDto.setName("New Survey");
        surveyDto.setDescription("New Description");
        
        List<SurveyItemCreateDto> items = new ArrayList<>();
        SurveyItemCreateDto itemDto = new SurveyItemCreateDto();
        itemDto.setName("Question 1");
        itemDto.setDescription("First question");
        itemDto.setInputType(InputType.SINGLE);
        itemDto.setRequired(true);
        itemDto.setOptions(Arrays.asList("Option 1", "Option 2"));
        items.add(itemDto);
        surveyDto.setItems(items);

        Survey savedSurvey = new Survey();
        savedSurvey.setId("2025040300001");
        savedSurvey.setName(surveyDto.getName());
        savedSurvey.setDescription(surveyDto.getDescription());

        SurveyItem savedItem = new SurveyItem();
        savedItem.setId("2025040300001");
        savedItem.setSurvey(savedSurvey);  // Add this line
        savedItem.setName(itemDto.getName());  // Add this line
        savedItem.setDescription(itemDto.getDescription());  // Add this line
        savedItem.setInputType(itemDto.getInputType());  // Add this line
        savedItem.setRequired(itemDto.isRequired());  // Add this line

        when(surveyRepository.save(any(Survey.class))).thenReturn(savedSurvey);
        when(surveyItemService.createSurveyItem(any(SurveyItem.class))).thenReturn(savedItem);

        // When
        Survey result = surveyService.createSurvey(surveyDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("2025040300001");  // Add this line
        assertThat(result.getName()).isEqualTo("New Survey");
        assertThat(result.getDescription()).isEqualTo("New Description");
        
        verify(surveyRepository).save(any(Survey.class));
        verify(surveyItemService).createSurveyItem(any(SurveyItem.class));
        verify(surveyItemOptionService, times(2)).createOption(any(SurveyItemOption.class));
    }

    @Test
    @DisplayName("Should successfully update existing survey")
    void updateSurvey_WithValidId_ShouldUpdateAndReturnSurvey() {
        // Given
        String surveyId = "2025040300001";  // Changed from Long to String
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
        String invalidId = "2025040399999";  // Changed from 999L to String
        Survey updatedSurvey = new Survey();
        when(surveyRepository.findById(invalidId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> surveyService.updateSurvey(invalidId, updatedSurvey));
        
        assertThat(exception)
            .hasMessageContaining("Survey not found")
            .hasMessageContaining(String.valueOf(invalidId));  // Convert Long to String for message comparison
        verify(surveyRepository).findById(invalidId);
        verify(surveyRepository, never()).save(any(Survey.class));
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent survey")
    void deleteSurvey_WithInvalidId_ShouldThrowException() {
        // Given
        String invalidId = "2025040399999";  // Changed from Long to String
        when(surveyRepository.findById(invalidId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> surveyService.deleteSurvey(invalidId));
        
        assertThat(exception)
            .hasMessageContaining("Survey not found")
            .hasMessageContaining(String.valueOf(invalidId));  // Convert Long to String for message comparison
        verify(surveyRepository).findById(invalidId);
        verify(surveyRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Should successfully delete existing survey")
    void deleteSurvey_ShouldDeleteSurvey() {
        // Given
        String surveyId = "2025040300001";  // Changed from Long to String
        when(surveyRepository.findById(surveyId)).thenReturn(Optional.of(testSurvey));

        // When
        surveyService.deleteSurvey(surveyId);

        // Then
        verify(surveyRepository).findById(surveyId);
        verify(surveyRepository).deleteById(surveyId);
    }
}