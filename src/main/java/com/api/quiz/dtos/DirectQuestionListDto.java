package com.api.quiz.dtos;

import com.api.quiz.entities.DirectQuestion;

public record DirectQuestionListDto (String questionText, String correctAnswer, Long id){

    public DirectQuestionListDto(DirectQuestion directQuestion){
        this(
                directQuestion.getQuestionText(),
                directQuestion.getCorrectAnswer(),
                directQuestion.getId()
        );
    }
}
