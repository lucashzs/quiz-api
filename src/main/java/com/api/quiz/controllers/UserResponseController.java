package com.api.quiz.controllers;

import com.api.quiz.dtos.QuizAnswerDto;
import com.api.quiz.services.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/response")
public class UserResponseController {

    private final QuizService quizService;
    public UserResponseController(QuizService quizService){
        this.quizService = quizService;
    }

    @PostMapping("/answerQuiz")
    public ResponseEntity<Boolean> answerQuiz(@RequestBody QuizAnswerDto quizAnswerDto) {
        boolean isCorrect = quizService.checkQuizAnswers(quizAnswerDto);
        return ResponseEntity.ok(isCorrect);
    }
}
