package com.example.ranyoungonboarding.api;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnswerRequest {
    
    private Long questionId;
    
    // 단답형, 장문형, 단일 선택형 응답용
    private String value;
    
    // 다중 선택형 응답용
    private List<String> values;
}
