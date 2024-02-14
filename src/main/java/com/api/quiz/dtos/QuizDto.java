package com.api.quiz.dtos;

import com.api.quiz.entities.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public record QuizDto(
        @NotBlank(message = "You need to have a name to create a quiz!") String nameQuiz,
        @NotBlank(message = "You need to have a visibility to create a quiz!") String visibility,
        String accessPassword,
        @NotNull(message = "You need to have a id to create a quiz!") Long userId,
        @NotNull(message = "You need to have a questions to create a quiz!") List<QuestionDto> questions) {
    public QuizDto(Quiz quiz) {
        this(
                quiz.getNameQuiz(),
                quiz.getVisibility(),
                quiz.getAccessPassword(),
                quiz.getUser().getId(),
                quiz.getQuestions().stream().map(QuestionDto::new).collect(Collectors.toList())
        );
    }
}
