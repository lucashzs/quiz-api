package com.api.quiz.dtos;

import java.util.List;

public record QuizResponseDto (String nameQuiz, String visibility, String accessPassword, List<Object> questions) {
}
