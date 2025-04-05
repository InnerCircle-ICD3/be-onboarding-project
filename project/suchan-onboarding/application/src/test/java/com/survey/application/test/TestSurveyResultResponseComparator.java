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
        if (!Objects.equals(expected.getSurveyId(), actual.getSurveyId())) {
            System.out.println("다른 필드: surveyId - 기대값: " + expected.getSurveyId() + ", 실제값: " + actual.getSurveyId());
            return false;
        }
        if (!Objects.equals(expected.getVersion(), actual.getVersion())) {
            System.out.println("다른 필드: version - 기대값: " + expected.getVersion() + ", 실제값: " + actual.getVersion());
            return false;
        }
        if (!Objects.equals(expected.getTitle(), actual.getTitle())) {
            System.out.println("다른 필드: title - 기대값: " + expected.getTitle() + ", 실제값: " + actual.getTitle());
            return false;
        }
        if (!Objects.equals(expected.getDescription(), actual.getDescription())) {
            System.out.println("다른 필드: description - 기대값: " + expected.getDescription() + ", 실제값: " + actual.getDescription());
            return false;
        }

        // 설문 옵션 리스트 비교
        List<SurveyOptionResultDto> expectedOptions = expected.getSurveyOptions();
        List<SurveyOptionResultDto> actualOptions = actual.getSurveyOptions();

        if (expectedOptions.size() != actualOptions.size()) {
            System.out.println("다른 필드: surveyOptions.size - 기대값: " + expectedOptions.size() + ", 실제값: " + actualOptions.size());
            return false;
        }

        // 옵션 ID로 정렬
        List<SurveyOptionResultDto> sortedExpectedOptions = new ArrayList<>(expectedOptions);
        List<SurveyOptionResultDto> sortedActualOptions = new ArrayList<>(actualOptions);

        Comparator<SurveyOptionResultDto> comparator = Comparator.comparing(SurveyOptionResultDto::getId);
        sortedExpectedOptions.sort(comparator);
        sortedActualOptions.sort(comparator);

        // ID 목록 출력
        System.out.println("기대하는 옵션 ID: " + sortedExpectedOptions.stream().map(SurveyOptionResultDto::getId).toList());
        System.out.println("실제 옵션 ID: " + sortedActualOptions.stream().map(SurveyOptionResultDto::getId).toList());

        // 정렬된 리스트 비교
        for (int i = 0; i < sortedExpectedOptions.size(); i++) {
            if (!areSurveyOptionsEqual(sortedExpectedOptions.get(i), sortedActualOptions.get(i), i)) {
                return false;
            }
        }

        return true;
    }

    private static boolean areSurveyOptionsEqual(SurveyOptionResultDto expected, SurveyOptionResultDto actual, int index) {
        if (expected == actual) return true;
        if (expected == null || actual == null) {
            System.out.println("옵션 " + index + ": null 값 비교 실패");
            return false;
        }

        if (!Objects.equals(expected.getId(), actual.getId())) {
            System.out.println("옵션 " + index + " - 다른 필드: id - 기대값: " + expected.getId() + ", 실제값: " + actual.getId());
            return false;
        }
        if (!Objects.equals(expected.getTitle(), actual.getTitle())) {
            System.out.println("옵션 " + index + " - 다른 필드: title - 기대값: " + expected.getTitle() + ", 실제값: " + actual.getTitle());
            return false;
        }
        if (!Objects.equals(expected.getDescription(), actual.getDescription())) {
            System.out.println("옵션 " + index + " - 다른 필드: description - 기대값: " + expected.getDescription() + ", 실제값: " + actual.getDescription());
            return false;
        }
        if (!Objects.equals(expected.getIsNecessary(), actual.getIsNecessary())) {
            System.out.println("옵션 " + index + " - 다른 필드: isNecessary - 기대값: " + expected.getIsNecessary() + ", 실제값: " + actual.getIsNecessary());
            return false;
        }

        return areInputFormsEqual(expected.getInputForm(), actual.getInputForm(), index);
    }

    private static boolean areInputFormsEqual(InputFormResultDto expected, InputFormResultDto actual, int optionIndex) {
        if (expected == actual) return true;
        if (expected == null || actual == null) {
            System.out.println("옵션 " + optionIndex + " - 입력 폼: null 값 비교 실패");
            return false;
        }

        if (!Objects.equals(expected.getId(), actual.getId())) {
            System.out.println("옵션 " + optionIndex + " - 입력 폼 - 다른 필드: id - 기대값: " + expected.getId() + ", 실제값: " + actual.getId());
            return false;
        }
        if (!Objects.equals(expected.getQuestion(), actual.getQuestion())) {
            System.out.println("옵션 " + optionIndex + " - 입력 폼 - 다른 필드: question - 기대값: " + expected.getQuestion() + ", 실제값: " + actual.getQuestion());
            return false;
        }

        // TextResult 비교
        if (expected.getTextResult() != null && actual.getTextResult() != null) {
            if (!areTextResultsEqual(expected.getTextResult(), actual.getTextResult(), optionIndex)) {
                return false;
            }
        } else if (expected.getTextResult() != null) {
            System.out.println("옵션 " + optionIndex + " - 입력 폼: 기대값에는 텍스트 결과가 있지만 실제값에는 없음");
            return false;
        } else if (actual.getTextResult() != null) {
            System.out.println("옵션 " + optionIndex + " - 입력 폼: 실제값에는 텍스트 결과가 있지만 기대값에는 없음");
            return false;
        }

        // ChoiceResult 비교
        if (expected.getChoiceResult() != null && actual.getChoiceResult() != null) {
            return areChoiceResultsEqual(expected.getChoiceResult(), actual.getChoiceResult(), optionIndex);
        } else if (expected.getChoiceResult() != null) {
            System.out.println("옵션 " + optionIndex + " - 입력 폼: 기대값에는 선택 결과가 있지만 실제값에는 없음");
            return false;
        } else if (actual.getChoiceResult() != null) {
            System.out.println("옵션 " + optionIndex + " - 입력 폼: 실제값에는 선택 결과가 있지만 기대값에는 없음");
            return false;
        }

        return true;
    }

    private static boolean areTextResultsEqual(TextResultDto expected, TextResultDto actual, int optionIndex) {
        if (expected == actual) return true;
        if (expected == null || actual == null) {
            System.out.println("옵션 " + optionIndex + " - 텍스트 결과: null 값 비교 실패");
            return false;
        }

        if (!Objects.equals(expected.getId(), actual.getId())) {
            System.out.println("옵션 " + optionIndex + " - 텍스트 결과 - 다른 필드: id - 기대값: " + expected.getId() + ", 실제값: " + actual.getId());
            return false;
        }
        if (!Objects.equals(expected.getTextType(), actual.getTextType())) {
            System.out.println("옵션 " + optionIndex + " - 텍스트 결과 - 다른 필드: textType - 기대값: " + expected.getTextType() + ", 실제값: " + actual.getTextType());
            return false;
        }
        if (!Objects.equals(expected.getAnswer(), actual.getAnswer())) {
            System.out.println("옵션 " + optionIndex + " - 텍스트 결과 - 다른 필드: answer - 기대값: " + expected.getAnswer() + ", 실제값: " + actual.getAnswer());
            return false;
        }
        return true;
    }

    private static boolean areChoiceResultsEqual(ChoiceResultDto expected, ChoiceResultDto actual, int optionIndex) {
        if (expected == actual) return true;
        if (expected == null || actual == null) {
            System.out.println("옵션 " + optionIndex + " - 선택 결과: null 값 비교 실패");
            return false;
        }

        if (!Objects.equals(expected.getId(), actual.getId())) {
            System.out.println("옵션 " + optionIndex + " - 선택 결과 - 다른 필드: id - 기대값: " + expected.getId() + ", 실제값: " + actual.getId());
            return false;
        }
        if (!Objects.equals(expected.getChoiceType(), actual.getChoiceType())) {
            System.out.println("옵션 " + optionIndex + " - 선택 결과 - 다른 필드: choiceType - 기대값: " + expected.getChoiceType() + ", 실제값: " + actual.getChoiceType());
            return false;
        }

        // options 리스트 비교 (순서 무시)
        if (!areListsEqualIgnoringOrder(expected.getOptions(), actual.getOptions())) {
            System.out.println("옵션 " + optionIndex + " - 선택 결과 - 다른 필드: options - 기대값: " + expected.getOptions() + ", 실제값: " + actual.getOptions());
            return false;
        }

        // selectedOptions 리스트 비교 (순서 무시)
        if (!areListsEqualIgnoringOrder(expected.getSelectedOptions(), actual.getSelectedOptions())) {
            System.out.println("옵션 " + optionIndex + " - 선택 결과 - 다른 필드: selectedOptions - 기대값: " + expected.getSelectedOptions() + ", 실제값: " + actual.getSelectedOptions());
            return false;
        }

        return true;
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

    // 디버깅용 도우미 메서드
    public static void compareAndPrintDifferences(GetAllSurveyResultResponse expected, GetAllSurveyResultResponse actual) {
        System.out.println("=== 상세 비교 시작 ===");

        if (expected == null && actual == null) {
            System.out.println("두 객체 모두 null입니다.");
            return;
        }

        if (expected == null) {
            System.out.println("기대값이 null입니다.");
            return;
        }

        if (actual == null) {
            System.out.println("실제값이 null입니다.");
            return;
        }

        System.out.println("기본 필드 비교:");
        System.out.println("surveyId - 기대값: " + expected.getSurveyId() + ", 실제값: " + actual.getSurveyId());
        System.out.println("version - 기대값: " + expected.getVersion() + ", 실제값: " + actual.getVersion());
        System.out.println("title - 기대값: " + expected.getTitle() + ", 실제값: " + actual.getTitle());
        System.out.println("description - 기대값: " + expected.getDescription() + ", 실제값: " + actual.getDescription());

        List<SurveyOptionResultDto> expectedOptions = expected.getSurveyOptions();
        List<SurveyOptionResultDto> actualOptions = actual.getSurveyOptions();

        System.out.println("옵션 수 - 기대값: " + expectedOptions.size() + ", 실제값: " + actualOptions.size());

        // 옵션 ID 출력
        System.out.println("기대하는 옵션 ID 목록: " + expectedOptions.stream().map(SurveyOptionResultDto::getId).toList());
        System.out.println("실제 옵션 ID 목록: " + actualOptions.stream().map(SurveyOptionResultDto::getId).toList());

        // 기타 필드 상세 정보
        System.out.println("\n각 옵션 상세 정보 비교:");
        int minSize = Math.min(expectedOptions.size(), actualOptions.size());

        for (int i = 0; i < minSize; i++) {
            SurveyOptionResultDto eOpt = expectedOptions.get(i);
            SurveyOptionResultDto aOpt = actualOptions.get(i);

            System.out.println("\n옵션 " + i + ":");
            System.out.println("  ID - 기대값: " + eOpt.getId() + ", 실제값: " + aOpt.getId());
            System.out.println("  제목 - 기대값: " + eOpt.getTitle() + ", 실제값: " + aOpt.getTitle());
            System.out.println("  설명 - 기대값: " + eOpt.getDescription() + ", 실제값: " + aOpt.getDescription());
            System.out.println("  필수 여부 - 기대값: " + eOpt.getIsNecessary() + ", 실제값: " + aOpt.getIsNecessary());

            InputFormResultDto eForm = eOpt.getInputForm();
            InputFormResultDto aForm = aOpt.getInputForm();

            if (eForm != null && aForm != null) {
                System.out.println("  입력 폼:");
                System.out.println("    ID - 기대값: " + eForm.getId() + ", 실제값: " + aForm.getId());
                System.out.println("    질문 - 기대값: " + eForm.getQuestion() + ", 실제값: " + aForm.getQuestion());

                if (eForm.getTextResult() != null && aForm.getTextResult() != null) {
                    TextResultDto eText = eForm.getTextResult();
                    TextResultDto aText = aForm.getTextResult();

                    System.out.println("    텍스트 결과:");
                    System.out.println("      ID - 기대값: " + eText.getId() + ", 실제값: " + aText.getId());
                    System.out.println("      타입 - 기대값: " + eText.getTextType() + ", 실제값: " + aText.getTextType());
                    System.out.println("      답변 - 기대값: " + eText.getAnswer() + ", 실제값: " + aText.getAnswer());
                } else if (eForm.getChoiceResult() != null && aForm.getChoiceResult() != null) {
                    ChoiceResultDto eChoice = eForm.getChoiceResult();
                    ChoiceResultDto aChoice = aForm.getChoiceResult();

                    System.out.println("    선택 결과:");
                    System.out.println("      ID - 기대값: " + eChoice.getId() + ", 실제값: " + aChoice.getId());
                    System.out.println("      타입 - 기대값: " + eChoice.getChoiceType() + ", 실제값: " + aChoice.getChoiceType());
                    System.out.println("      옵션들 - 기대값: " + eChoice.getOptions() + ", 실제값: " + aChoice.getOptions());
                    System.out.println("      선택된 옵션들 - 기대값: " + eChoice.getSelectedOptions() + ", 실제값: " + aChoice.getSelectedOptions());
                }
            }
        }

        System.out.println("=== 상세 비교 종료 ===");
    }
}