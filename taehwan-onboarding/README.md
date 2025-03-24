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
    "type": QuestionType, 
    "is_required": "항목 필수 여부"
}
```
### QuestionType(항목 입력 형태)
```json
{
    "short": "단답형",
    "long": "장문형",
    "single_select_list": "단일 선택 리스트",
    "multi_select_list:": "다중 선택 리스트"
}
```