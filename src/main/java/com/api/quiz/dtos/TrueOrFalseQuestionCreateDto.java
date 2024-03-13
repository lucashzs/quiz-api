package com.api.quiz.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TrueOrFalseQuestionCreateDto {

    @NotBlank(message = "The question cannot be empty")
    private String questionText;

    @NotNull(message = "The Correct Answer cannot be empty")
    private Boolean correctAnswer;

    public TrueOrFalseQuestionCreateDto() {
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
