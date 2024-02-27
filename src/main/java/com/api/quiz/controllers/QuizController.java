package com.api.quiz.controllers;

import com.api.quiz.dtos.QuizAnswerDto;
import com.api.quiz.dtos.QuizDto;
import com.api.quiz.dtos.QuizResponseDto;
import com.api.quiz.services.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody @Valid QuizDto quizDto) {
        return this.quizService.createQuiz(quizDto);
    }

    @GetMapping
    public ResponseEntity<List<QuizDto>> getAllQuizzes() {
        return this.quizService.getAllQuiz();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable Long id) {
        return this.quizService.getQuizById(id);
    }

    @PostMapping("/answers")
    public ResponseEntity<QuizResponseDto> answerQuiz(@RequestBody QuizAnswerDto answers) {
        return this.quizService.checkQuizAnswers(answers);
    }
}
