package com.api.quiz.dtos;

import com.api.quiz.entities.Quiz;
import jakarta.validation.constraints.NotBlank;
public record QuizDto(
        @NotBlank(message = "You need to have a name to create a quiz!") String nameQuiz,
        @NotBlank(message = "You need to have a visibility to create a quiz!") String visibility,
        String accessPassword) {
    public QuizDto(Quiz quiz) {
        this(
                quiz.getNameQuiz(),
                quiz.getVisibility(),
                quiz.getAccessPassword()
        );
    }
}
