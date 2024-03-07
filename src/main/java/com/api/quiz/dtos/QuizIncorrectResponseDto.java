package com.api.quiz.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QuizIncorrectResponseDto {
    private String message;
    private List<QuestionDto> incorrectQuestion;

    public QuizIncorrectResponseDto() {
    }

    public QuizIncorrectResponseDto(String message, List<QuestionDto> incorrectQuestionIds) {
        this.message = message;
        this.incorrectQuestion = incorrectQuestionIds;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<QuestionDto> getIncorrectQuestion() {
        return incorrectQuestion;
    }

    public void setIncorrectQuestion(List<QuestionDto> incorrectQuestion) {
        this.incorrectQuestion = incorrectQuestion;
    }
}

