package com.api.quiz.dtos;

import com.api.quiz.entities.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TrueOrFalseQuestionDto{

    @NotBlank(message = "The question cannot be empty")
    private String questionText;

    @NotNull(message = "The Correct Answer cannot be empty")
    private Boolean correctAnswer;

    public TrueOrFalseQuestionDto() {
    }

    public TrueOrFalseQuestionDto(String questionText, Boolean correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public TrueOrFalseQuestionDto(TrueOrFalseQuestionDto trueOrFalseQuestionDto, Quiz quiz) {
        this.questionText = trueOrFalseQuestionDto.getQuestionText();
        this.correctAnswer = trueOrFalseQuestionDto.getCorrectAnswer();
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
