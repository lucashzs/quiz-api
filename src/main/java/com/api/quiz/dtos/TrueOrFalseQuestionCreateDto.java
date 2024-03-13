package com.api.quiz.dtos;

import com.api.quiz.entities.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TrueOrFalseQuestionCreateDto {

    @NotBlank(message = "The question cannot be empty")
    private String questionText;

    @NotNull(message = "The Correct Answer cannot be empty")
    private Boolean correctAnswer;

    public TrueOrFalseQuestionCreateDto() {
    }

    public TrueOrFalseQuestionCreateDto(String questionText, Boolean correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public TrueOrFalseQuestionCreateDto(TrueOrFalseQuestionCreateDto trueOrFalseQuestionCreateDto, Quiz quiz) {
        this.questionText = trueOrFalseQuestionCreateDto.getQuestionText();
        this.correctAnswer = trueOrFalseQuestionCreateDto.getCorrectAnswer();
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
