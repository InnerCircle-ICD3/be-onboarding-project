package com.example.ranyoungonboarding.api;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubmitResponseRequest {
    
    @NotEmpty
    private List<AnswerRequest> answers;
}

