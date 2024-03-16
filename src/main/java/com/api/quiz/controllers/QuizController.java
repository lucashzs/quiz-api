package com.api.quiz.controllers;

import com.api.quiz.dtos.QuizAnswerDto;
import com.api.quiz.dtos.QuizDto;
import com.api.quiz.dtos.QuizIncorrectResponseDto;
import com.api.quiz.dtos.QuizResponseDto;
import com.api.quiz.entities.Rank;
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
    public ResponseEntity<QuizResponseDto> getQuiz(@PathVariable Long id) {
        return this.quizService.getQuiz(id);
    }

    @PostMapping("/answers")
    public ResponseEntity<QuizIncorrectResponseDto> answerQuiz(@RequestBody QuizAnswerDto answers) {
        return this.quizService.checkQuizAnswers(answers);
    }

    @GetMapping("/ranking/{quizId}")
    public List<Rank> getRankingByQuiz(@PathVariable long quizId){
        return quizService.getRankingByQuiz(quizId);
    }
}
