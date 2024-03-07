package com.api.quiz.services;

import com.api.quiz.dtos.AlternativeQuestionDto;
import com.api.quiz.dtos.DirectQuestionDto;
import com.api.quiz.dtos.TrueOrFalseQuestionDto;
import com.api.quiz.entities.AlternativeQuestion;
import com.api.quiz.entities.Question;
import com.api.quiz.entities.Quiz;
import com.api.quiz.repositories.QuestionsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class QuestionsService {

    private final QuestionsRepository questionsRepository;
    private final QuizService quizService;

    public QuestionsService(QuestionsRepository questionsRepository, QuizService quizService) {
        this.questionsRepository = questionsRepository;
        this.quizService = quizService;
    }

    public ResponseEntity<Object> createDirectQuestions(DirectQuestionDto directQuestionDto, Long quizId) {
        Quiz quiz = quizService.findById(quizId);
        var newQuestion = new Question(directQuestionDto, quiz);


        this.questionsRepository.save(newQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Question Successfully!");
    }

    public ResponseEntity<Object> createTrueOrFalseQuestion(TrueOrFalseQuestionDto trueOrFalseQuestionDto, Long quizId) {
        Quiz quiz = quizService.findById(quizId);
        var newQuestion = new Question(trueOrFalseQuestionDto, quiz);


        this.questionsRepository.save(newQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Question Successfully!");
    }

    public ResponseEntity<Object> createAlternativeQuestion(AlternativeQuestionDto alternativeQuestionDto, Long quizId) {
        Quiz quiz = quizService.findById(quizId);
        var newQuestion = new AlternativeQuestion(alternativeQuestionDto, quiz);

        List<String> options = alternativeQuestionDto.getOptions();
        newQuestion.setOptions(options);

        if (options != null && !options.isEmpty()) {
            newQuestion.setOptions(options);
        }
        if (options == null || options.size() < 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("At least two options are required for an alternative question!");
        }
        if (options.stream().noneMatch(Objects::isNull)) {
            if (options.stream().distinct().count() != options.size()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Options must be unique!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Options cannot be null!");
        }

        this.questionsRepository.save(newQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Question Successfully!");
    }
}
