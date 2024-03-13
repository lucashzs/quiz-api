package com.api.quiz.dtos;

import com.api.quiz.entities.AlternativeQuestion;

import java.util.List;

public record AlternativeQuestionListDto(String questionText, String correctAnswer, List<String>alternatives, Long id) {

    public AlternativeQuestionListDto (AlternativeQuestion alternativeQuestion){
        this(
                alternativeQuestion.getQuestionText(),
                alternativeQuestion.getCorrectAnswer(),
                alternativeQuestion.getAlternatives(),
                alternativeQuestion.getId()
        );
    }
}
