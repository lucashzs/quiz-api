package com.api.quiz.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class AlternativeQuestionCreateDto {

    @NotBlank(message = "The question cannot be empty")
    private String questionText;

    @NotBlank(message = "The Correct Answer cannot be empty")
    private String correctAnswer;


    private List<String> alternatives;

    public List<String> getAlternatives() {
        return alternatives;
    }

    public AlternativeQuestionCreateDto() {
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

    public void setAlternatives(List<String> alternatives) {
        this.alternatives = alternatives;
    }
    public AlternativeQuestionCreateDto(Long id, List<String> alternatives) {
        this.alternatives = alternatives;
    }
}
