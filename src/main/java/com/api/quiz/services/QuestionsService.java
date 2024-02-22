package com.api.quiz.services;

import com.api.quiz.dtos.QuestionDto;
import com.api.quiz.entities.Question;
import com.api.quiz.entities.Quiz;
import com.api.quiz.repositories.QuestionsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class QuestionsService {

    private final QuestionsRepository questionsRepository;
    private final QuizService quizService;

    public QuestionsService(QuestionsRepository questionsRepository, QuizService quizService) {
        this.questionsRepository = questionsRepository;
        this.quizService = quizService;
    }

    public ResponseEntity<Object> createDirectQuestions(QuestionDto questionDto, Long quizId) {
        Quiz quiz = quizService.findById(quizId);

        var newQuestion = new Question(questionDto, quiz);
        this.questionsRepository.save(newQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Question Successfully!");
    }
}
