package com.api.quiz.dtos;

import com.api.quiz.entities.Question;

public record QuestionDto(String questionText, String correctAnswer) {
    public QuestionDto(Question question) {
        this(question.getQuestionText(), question.getCorrectAnswer());
    }
}