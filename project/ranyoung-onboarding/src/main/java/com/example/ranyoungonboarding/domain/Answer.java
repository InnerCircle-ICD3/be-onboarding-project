package com.example.ranyoungonboarding.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id", nullable = false)
    private Response response;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    // For single answer types (SHORT_ANSWER, LONG_ANSWER, SINGLE_CHOICE)
    private String textValue;

    // For MULTIPLE_CHOICE
    @ElementCollection
    @CollectionTable(name = "answer_values", joinColumns = @JoinColumn(name = "answer_id"))
    @Column(name = "answer_value")
    private List<String> multipleValues = new ArrayList<>();
}