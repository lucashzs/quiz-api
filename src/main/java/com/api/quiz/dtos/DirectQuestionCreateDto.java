package com.api.quiz.dtos;

import com.api.quiz.entities.Quiz;
import jakarta.validation.constraints.NotBlank;

public class DirectQuestionCreateDto {
    @NotBlank(message = "The question cannot be empty")
    private String questionText;

    @NotBlank(message = "The Correct Answer cannot be empty")
    private String correctAnswer;

    public DirectQuestionCreateDto() {
    }

    public DirectQuestionCreateDto(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public DirectQuestionCreateDto(DirectQuestionCreateDto directQuestionCreateDto, Quiz quiz) {
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}