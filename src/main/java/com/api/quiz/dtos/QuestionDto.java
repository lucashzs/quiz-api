package com.api.quiz.dtos;

import com.api.quiz.entities.Question;
import com.api.quiz.enums.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionDto(
        @NotBlank(message = "The question cannot be empty") String questionText,
        @NotBlank(message = "The Correct Answer cannot be empty") String correctAnswer,
        @NotNull(message = "The ques question type cannot be empty") QuestionType questionType

) {
    public QuestionDto(Question question, Long id) {
        this(question.getQuestionText(), question.getCorrectAnswer(), question.getQuestionType());
    }
}