package com.api.quiz.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QuizIncorrectResponseDto {
    private String message;
    private List<Object> incorrectQuestion;

    public QuizIncorrectResponseDto() {
    }

    public QuizIncorrectResponseDto(String message, List<Object> incorrectQuestionIds) {
        this.message = message;
        this.incorrectQuestion = incorrectQuestionIds;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Object> getIncorrectQuestion() {
        return incorrectQuestion;
    }

    public void setIncorrectQuestion(List<Object> incorrectQuestion) {
        this.incorrectQuestion = incorrectQuestion;
    }
}

