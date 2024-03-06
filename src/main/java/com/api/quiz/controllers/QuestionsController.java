package com.api.quiz.controllers;

import com.api.quiz.dtos.AlternativeQuestionDto;
import com.api.quiz.dtos.QuestionOutputDto;
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
    public ResponseEntity<Object> createQuestionsDirect(@RequestBody @Valid QuestionOutputDto questionOutputDto, @PathVariable Long quizId) {
        return this.questionsService.createDirectQuestions(questionOutputDto, quizId);
    }

    @PostMapping("/true-or-false")
    public ResponseEntity<Object> createTrueOrFalseQuestion(@RequestBody @Valid QuestionOutputDto questionOutputDto, @PathVariable Long quizId) {
        return this.questionsService.createTrueOrFalseQuestion(questionOutputDto, quizId);
    }

    @PostMapping("/alternative")
    public ResponseEntity<Object> createAlternativeQuestion(@RequestBody @Valid AlternativeQuestionDto alternativeQuestionDto, @PathVariable Long quizId) {
        return this.questionsService.createAlternativeQuestion(alternativeQuestionDto, quizId);
    }
}
