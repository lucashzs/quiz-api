package com.api.quiz.controllers;

import com.api.quiz.dtos.QuestionDto;
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
    public ResponseEntity<Object> createQuestionsDirect (@RequestBody @Valid QuestionDto questionDto, @PathVariable Long quizId){
        return this.questionsService.createDirectQuestions(questionDto, quizId);
    }
}
