package com.survey.application.test;

import com.survey.application.dto.response.GetAllSurveyResultResponse;
import com.survey.application.dto.response.GetAllSurveyResultResponse.ChoiceResultDto;
import com.survey.application.dto.response.GetAllSurveyResultResponse.InputFormResultDto;
import com.survey.application.dto.response.GetAllSurveyResultResponse.SurveyOptionResultDto;
import com.survey.application.dto.response.GetAllSurveyResultResponse.TextResultDto;

import java.util.*;

public class TestSurveyResultResponseComparator {

    public static boolean areEqual(GetAllSurveyResultResponse expected, GetAllSurveyResultResponse actual) {
        if (expected == actual) return true;
        if (expected == null || actual == null) return false;

        // 기본 필드 비교
        if (!Objects.equals(expected.getSurveyId(), actual.getSurveyId())) return false;
        if (!Objects.equals(expected.getVersion(), actual.getVersion())) return false;
        if (!Objects.equals(expected.getTitle(), actual.getTitle())) return false;
        if (!Objects.equals(expected.getDescription(), actual.getDescription())) return false;

        // 설문 옵션 리스트 비교
        List<SurveyOptionResultDto> expectedOptions = expected.getSurveyOptions();
        List<SurveyOptionResultDto> actualOptions = actual.getSurveyOptions();

        if (expectedOptions.size() != actualOptions.size()) return false;

        // 옵션 ID로 정렬
        List<SurveyOptionResultDto> sortedExpectedOptions = new ArrayList<>(expectedOptions);
        List<SurveyOptionResultDto> sortedActualOptions = new ArrayList<>(actualOptions);

        Comparator<SurveyOptionResultDto> comparator = Comparator.comparing(SurveyOptionResultDto::getId);
        sortedExpectedOptions.sort(comparator);
        sortedActualOptions.sort(comparator);

        // 정렬된 리스트 비교
        for (int i = 0; i < sortedExpectedOptions.size(); i++) {
            if (!areSurveyOptionsEqual(sortedExpectedOptions.get(i), sortedActualOptions.get(i))) {
                return false;
            }
        }

        return true;
    }

    private static boolean areSurveyOptionsEqual(SurveyOptionResultDto expected, SurveyOptionResultDto actual) {
        if (expected == actual) return true;
        if (expected == null || actual == null) return false;

        if (!Objects.equals(expected.getTitle(), actual.getTitle())) return false;
        if (!Objects.equals(expected.getDescription(), actual.getDescription())) return false;
        if (!Objects.equals(expected.getIsNecessary(), actual.getIsNecessary())) return false;

        return areInputFormsEqual(expected.getInputForm(), actual.getInputForm());
    }

    private static boolean areInputFormsEqual(InputFormResultDto expected, InputFormResultDto actual) {
        if (expected == actual) return true;
        if (expected == null || actual == null) return false;

        if (!Objects.equals(expected.getQuestion(), actual.getQuestion())) return false;

        // TextResult 비교
        if (expected.getTextResult() != null && actual.getTextResult() != null) {
            if (!areTextResultsEqual(expected.getTextResult(), actual.getTextResult())) {
                return false;
            }
        } else if (expected.getTextResult() != null || actual.getTextResult() != null) {
            return false;
        }

        // ChoiceResult 비교
        if (expected.getChoiceResult() != null && actual.getChoiceResult() != null) {
            return areChoiceResultsEqual(expected.getChoiceResult(), actual.getChoiceResult());
        } else return expected.getChoiceResult() == null && actual.getChoiceResult() == null;
    }

    private static boolean areTextResultsEqual(TextResultDto expected, TextResultDto actual) {
        if (expected == actual) return true;
        if (expected == null || actual == null) return false;

        if (!Objects.equals(expected.getTextType(), actual.getTextType())) return false;
        return Objects.equals(expected.getAnswer(), actual.getAnswer());
    }

    private static boolean areChoiceResultsEqual(ChoiceResultDto expected, ChoiceResultDto actual) {
        if (expected == actual) return true;
        if (expected == null || actual == null) return false;

        if (!Objects.equals(expected.getChoiceType(), actual.getChoiceType())) return false;

        // options 리스트 비교 (순서 무시)
        if (!areListsEqualIgnoringOrder(expected.getOptions(), actual.getOptions())) {
            return false;
        }

        // selectedOptions 리스트 비교 (순서 무시)
        return areListsEqualIgnoringOrder(expected.getSelectedOptions(), actual.getSelectedOptions());
    }

    private static boolean areListsEqualIgnoringOrder(List<String> list1, List<String> list2) {
        if (list1 == list2) return true;
        if (list1 == null || list2 == null) return false;
        if (list1.size() != list2.size()) return false;

        List<String> sortedList1 = new ArrayList<>(list1);
        List<String> sortedList2 = new ArrayList<>(list2);

        Collections.sort(sortedList1);
        Collections.sort(sortedList2);

        return sortedList1.equals(sortedList2);
    }
}