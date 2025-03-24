# Survey Service
## 개요
* 양식(form)을 생성하고, 생성된 양식 기반의 응답을 받을 수 있는 서비스를 구현한다.
* e.g.) Google Forms. Tally, TypeForm
## 설문조사 생성 API
### RequestBody
```json
{
    "title": "설문조사 이름",
    "description": "설문조사 설명",
    "question": "설문 받을 항목"
}
```
### Question(설문 받을 항목, Min = 1, Max = 10)
```json
{
    "title": "항목이름",
    "description": "항목설명", 
    "type": FormField, 
    "is_required": "항목 필수 여부"
}
```
### FormField(항목 입력 형태)
```json
{
    "short": "단답형",
    "long": "장문형",
    "single_select_list": "단일 선택 리스트",
    "multi_select_list:": "다중 선택 리스트"
}
```
```java
public enum InputType {
    SHORT_ANSWER,
    LONG_ANSWER,
    SINGLE_SELECT,
    MULTI_SELECT
}

public class FormField {
    private String fieldName;
    private InputType inputType;
    private List<String> selections;  // 리스트용 후보 옵션들
    
    // 선택 리스트인 경우에만 선택 옵션 추가 가능하도록 메서드 제공
    // 무조건 list or 타입에 따라 받는 것 고민
    public void addSelectionChoices(List<String> choices) {
        if (inputType == InputType.SINGLE_SELECT || inputType == InputType.MULTI_SELECT) {
            this.selectionChoices = choices;
        }
    }
}
```