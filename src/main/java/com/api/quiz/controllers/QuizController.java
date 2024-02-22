package com.api.quiz.controllers;

import com.api.quiz.dtos.QuizDto;
import com.api.quiz.services.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;
    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create (@RequestBody @Valid QuizDto quizDto){
        return this.quizService.createQuiz(quizDto);
    }
}
