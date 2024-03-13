package com.api.quiz.dtos;

import com.api.quiz.entities.TrueOrFalseQuestion;

public record TrueOrFalseQuestionListDto (String questionText, Boolean correctAnswer, Long id){

    public TrueOrFalseQuestionListDto (TrueOrFalseQuestion trueOrFalseQuestion){
        this(
                trueOrFalseQuestion.getQuestionText(),
                trueOrFalseQuestion.getCorrectAnswer(),
                trueOrFalseQuestion.getId()
        );
    }
}
