package com.hjpark.survey.config

class SurveySwaggerExamples {
    companion object {
        // 설문 생성 요청 예제 데이터
        const val SURVEY_REQUEST_EXAMPLE = """
        {
          "data": {
            "name": "고객 만족도 조사",
            "description": "서비스 이용 경험을 평가해주세요.",
            "questions": [
              {
                "name": "서비스 전체 만족도는 어떠신가요?",
                "description": "1부터 5까지 점수를 선택해주세요.",
                "type": "SINGLE_CHOICE",
                "required": true,
                "sequence": 1,
                "options": [
                  { 
                    "text": "매우 만족", 
                    "sequence": 1 
                  },
                  { 
                    "text": "만족", 
                    "sequence": 2 
                  },
                  { 
                    "text": "보통", 
                    "sequence": 3 
                  }
                ]
              },
              {
                "name": "개선이 필요한 부분을 작성해주세요.",
                "type": "LONG_ANSWER",
                "required": false,
                "sequence": 2,
                "options": null
              }
            ]
          }
        }
        """

        // 설문 수정 요청 예제 데이터
        const val UPDATE_SURVEY_REQUEST_EXAMPLE = """{
          "data": {
            "name": "고객 만족도 조사 (수정됨)",
            "description": "서비스 이용 경험을 평가해주세요. 수정된 설명입니다.",
            "questions": [
              {
                "id": 1,
                "name": "서비스 전체 만족도는 어떠신가요?",
                "description": "1부터 5까지 점수를 선택해주세요.",
                "type": "SINGLE_CHOICE",
                "required": true,
                "sequence": 1,
                "options": [
                  {
                    "id": 1,
                    "text": "매우 만족",
                    "sequence": 1
                  },
                  {
                    "id": 2,
                    "text": "만족",
                    "sequence": 2
                  },
                  {
                    "id": 3,
                    "text": "보통",
                    "sequence": 3
                  }
                ]
              },
              {
                "id": null,
                "name": "서비스 개선 사항을 작성해주세요.",
                "description": null,
                "type": "LONG_ANSWER",
                "required": false,
                "sequence": 2,
                "options": null
              }
            ]
          }
        }"""

        // 질문 종류별 데이터 설명
        const val QUESTION_TYPES_DESCRIPTION = """
        질문의 종류별로 제공해야 하는 데이터는 다음과 같습니다:
        
        1. SHORT_ANSWER (단답형):
           - 텍스트 값을 입력받는 질문입니다.
           - 예시:
             {
               "name": "이름을 입력해주세요.",
               "description": null,
               "type": "SHORT_ANSWER",
               "required": true,
               "sequence": 1,
               "options": null
             }

        2. LONG_ANSWER (장문형):
           - 긴 텍스트 값을 입력받는 질문입니다.
           - 예시:
             {
               "name": "서비스 개선 사항을 작성해주세요.",
               "description": null,
               "type": "LONG_ANSWER",
               "required": false,
               "sequence": 2,
               "options": null
             }

        3. SINGLE_CHOICE (단일 선택):
           - 하나의 옵션만 선택 가능한 질문입니다.
           - 예시:
             {
               "name": "서비스 전체 만족도는 어떠신가요?",
               "description": null,
               "type": "SINGLE_CHOICE",
               "required": true,
               "sequence": 3,
               "options": [
                 { 
                   "text": "매우 만족", 
                   "sequence": 1 
                 },
                 { 
                   "text": "만족", 
                   "sequence": 2 
                 },
                 { 
                   "text": "보통", 
                   "sequence": 3 
                 }
               ]
             }

        4. MULTIPLE_CHOICE (다중 선택):
           - 여러 옵션을 선택할 수 있는 질문입니다.
           - 예시:
             {
               "name": "선호하는 서비스 기능을 선택해주세요.",
               "description": null,
               "type": "MULTIPLE_CHOICE",
               "required": false,
               "sequence": 4,
               "options": [
                 { 
                   "text": "빠른 응답 시간", 
                   "sequence": 1 
                 },
                 { 
                   "text": "친절한 고객 서비스", 
                   "sequence": 2 
                 },
                 { 
                   "text": null, // 새 옵션 추가 가능
                   ...
                 }
               ]
             }
        """


        const val FULL_SURVEYDATA_EXAMPLE = """
        {
          "id": 1,
          "name": "고객 만족도 조사",
          "description": "서비스 이용 경험을 평가해주세요.",
          "createTime": "2025-04-05T10:00:00",
          "updateTime": "2025-04-05T11:00:00",
          "questions": [
            // ---------단일 선택형 (SINGLE_CHOICE)-------------
            {
              "id": 101,
              "name": "서비스 전체 만족도는 어떠신가요?",
              "description": "1부터 5까지 점수를 선택해주세요.",
              "type": "SINGLE_CHOICE",
              "required": true,
              "sequence": 1,
              "options": [
                {
                  "id": 201,
                  "text": "매우 만족",
                  "sequence": 1
                },
                {
                  "id": 202,
                  "text": "만족",
                  "sequence": 2
                },
                {
                  "id": 203,
                  "text": "보통",
                  "sequence": 3
                }
              ]
            },
            // ---------장문형 (LONG_ANSWER)-------------
            {
              "id": 102,
              "name": "개선이 필요한 부분을 작성해주세요.",
              "description": null,
              "type": "LONG_ANSWER",
              "required": false,
              "sequence": 2,
              "options": null
            },
            // ---------단답형 (SHORT_ANSWER)-------------
            {
              "id": 103,
              "name": "이름을 입력해주세요.",
              "description": null,
              "type": "SHORT_ANSWER",
              "required": true,
              "sequence": 3,
              "options": null
            },
            {
             // ---------다중선택형 (MULTIPLE_CHOICE)-------------
              "id": 104,
              "name": "선호하는 서비스 기능을 선택해주세요.",
              "description": null,
              "type": "MULTIPLE_CHOICE",
              "required": false,
              "sequence": 4,
              "options": [
                {
                  "id": 301,
                  "text": "빠른 응답 시간",
                  "sequence": 1
                },
                {
                  "id": 302,
                  "text": "친절한 고객 서비스",
                  "sequence": 2
                },
                {
                  "id": 303,
                  "text": "이벤트 캐시 제공",
                  "sequence": 3
                }
              ]
            }
          ]
        }
        """
    }
}
