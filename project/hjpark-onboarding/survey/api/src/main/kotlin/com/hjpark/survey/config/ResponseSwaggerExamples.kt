package com.hjpark.survey.config

class ResponseSwaggerExamples {
    companion object {
        // 설문 응답 요청 예제 데이터
        const val RESPONSE_REQUEST_EXAMPLE = """             
        {                                                 
          "surveyId": 1,                                  
          "respondentId": "user123",                      
          "answers": [                                    
            {                                             
              "questionId": 101,                          
              "textValue": null,                          
              "optionIds": [201]                          
            },                                            
            {                                             
              "questionId": 102,                          
              "textValue": "서비스 개선이 필요합니다",               
              "optionIds": null                           
            },                                            
            {                                             
              "questionId": 103,                          
              "textValue": "홍길동",                         
              "optionIds": null                           
            },                                            
            {                                             
              "questionId": 104,                          
              "textValue": null,                          
              "optionIds": [301, 302]                     
            }                                             
          ]                                               
        }                                                 
        """

        // 질문 종류별 데이터 설명
        const val RESPONSE_TYPES_DESCRIPTION = """
        질문의 종류별로 응답해야 하는 데이터는 다음과 같습니다:
        
        1. 단답형/장문형 질문 (SHORT_ANSWER/LONG_ANSWER)
               - textValue만 입력하고, optionIds는 null로 설정합니다.
               - 예시: { "questionId": 103, "textValue": "홍길동", "optionIds": null }
            
            2. 단일 선택 질문 (SINGLE_CHOICE)
               - optionIds에 정확히 하나의 값을 포함하고, textValue는 null로 설정합니다.
               - 예시: { "questionId": 101, "textValue": null, "optionIds": [201] }
            
            3. 다중 선택 질문 (MULTIPLE_CHOICE)
               - optionIds에 하나 이상의 값을 포함하고, textValue는 null로 설정합니다.
               - 예시: { "questionId": 104, "textValue": null, "optionIds": [301, 302] }
        """

        // 단일 설문에 대한 응답 정보들을 불러온 샘플 데이터
        const val FULL_RESPONSE_EXAMPLE = """
        {
            "success": true,
            "data": [
                {
                "responseId": 1,
                "respondentId": "user123",
                "status": "COMPLETED",
                "createTime": "2025-04-05T10:00:00",
                "answers": [
                    {
                    "questionId": 101,
                    "textValue": null,
                    "selectedOptions": [
                        {
                        "optionId": 201,
                        "text": "매우 만족",
                        "sequence": 1
                        }
                    ]
                    },
                    {
                    "questionId": 102,
                    "textValue": "서비스 개선이 필요합니다",
                    "selectedOptions": null
                    }
                ]
                }
            ]
           }
        """
    }
}
