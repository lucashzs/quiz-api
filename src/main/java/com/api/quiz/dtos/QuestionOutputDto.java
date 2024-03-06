package com.api.quiz.dtos;

import com.api.quiz.entities.Question;
import jakarta.validation.constraints.NotBlank;

public class QuestionOutputDto {
    @NotBlank(message = "The question cannot be empty")
    private String questionText;

    @NotBlank(message = "The Correct Answer cannot be empty")
    private String correctAnswer;

    public QuestionOutputDto() {
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

    public QuestionOutputDto(Question question, Long id) {
        this.questionText = question.getQuestionText();
        this.correctAnswer = question.getCorrectAnswer();
    }
}
