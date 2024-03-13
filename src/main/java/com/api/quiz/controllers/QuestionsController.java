package com.api.quiz.controllers;

import com.api.quiz.dtos.AlternativeQuestionCreateDto;
import com.api.quiz.dtos.DirectQuestionCreateDto;
import com.api.quiz.dtos.TrueOrFalseQuestionCreateDto;
import com.api.quiz.services.QuestionsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/{quizId}/create-questions")
@RestController
public class QuestionsController {

    private final QuestionsService questionsService;

    public QuestionsController(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @PostMapping("/direct")
    public ResponseEntity<Object> createQuestionsDirect(@RequestBody @Valid DirectQuestionCreateDto directQuestionCreateDto, @PathVariable Long quizId) {
        return this.questionsService.createDirectQuestions(directQuestionCreateDto, quizId);
    }

    @PostMapping("/true-or-false")
    public ResponseEntity<Object> createTrueOrFalseQuestion(@RequestBody @Valid TrueOrFalseQuestionCreateDto trueOrFalseQuestionCreateDto, @PathVariable Long quizId) {
        return this.questionsService.createTrueOrFalseQuestion(trueOrFalseQuestionCreateDto, quizId);
    }

    @PostMapping("/alternative")
    public ResponseEntity<Object> createAlternativeQuestion(@RequestBody @Valid AlternativeQuestionCreateDto alternativeQuestionCreateDto, @PathVariable Long quizId) {
        return this.questionsService.createAlternativeQuestion(alternativeQuestionCreateDto, quizId);
    }
}
