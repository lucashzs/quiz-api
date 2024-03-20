package com.api.quiz.services;

import com.api.quiz.dtos.AlternativeQuestionCreateDto;
import com.api.quiz.dtos.DirectQuestionCreateDto;
import com.api.quiz.dtos.TrueOrFalseQuestionCreateDto;
import com.api.quiz.repositories.AlternativeQuestionRepository;
import com.api.quiz.repositories.DirectQuestionRepository;
import com.api.quiz.repositories.TrueOrFalseQuestionRepository;
import com.api.quiz.entities.AlternativeQuestion;
import com.api.quiz.entities.DirectQuestion;
import com.api.quiz.entities.Quiz;
import com.api.quiz.entities.TrueOrFalseQuestion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class QuestionsService {

    private final AlternativeQuestionRepository alternativeQuestionRepository;
    private final DirectQuestionRepository directQuestionRepository;
    private final TrueOrFalseQuestionRepository trueOrFalseQuestionRepository;
    private final QuizService quizService;

    public QuestionsService(AlternativeQuestionRepository alternativeQuestionRepository, DirectQuestionRepository directQuestionRepository, TrueOrFalseQuestionRepository trueOrFalseQuestionRepository, QuizService quizService) {
        this.alternativeQuestionRepository = alternativeQuestionRepository;
        this.directQuestionRepository = directQuestionRepository;
        this.trueOrFalseQuestionRepository = trueOrFalseQuestionRepository;
        this.quizService = quizService;
    }

    public ResponseEntity<Object> createDirectQuestions(DirectQuestionCreateDto directQuestionCreateDto, Long quizId) {
        Quiz quiz = quizService.findById(quizId);
        var newQuestion = new DirectQuestion(directQuestionCreateDto, quiz);


        this.directQuestionRepository.save(newQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Direct Question Successfully!");
    }

    public ResponseEntity<Object> createTrueOrFalseQuestion(TrueOrFalseQuestionCreateDto trueOrFalseQuestionCreateDto, Long quizId) {
        Quiz quiz = quizService.findById(quizId);
        var newQuestion = new TrueOrFalseQuestion(trueOrFalseQuestionCreateDto, quiz);


        this.trueOrFalseQuestionRepository.save(newQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created True Or False Question Successfully!");
    }

    public ResponseEntity<Object> createAlternativeQuestion(AlternativeQuestionCreateDto alternativeQuestionCreateDto, Long quizId) {
        Quiz quiz = quizService.findById(quizId);
        var newQuestion = new AlternativeQuestion(alternativeQuestionCreateDto, quiz);

        List<String> alternatives = alternativeQuestionCreateDto.getAlternatives();
        newQuestion.setAlternatives(alternatives);

        if (alternatives != null && !alternatives.isEmpty()) {
            newQuestion.setAlternatives(alternatives);
        }
        if (alternatives == null || alternatives.size() < 2) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("At least two alternatives are required for an alternative question!");
        }
        if (alternatives.stream().noneMatch(Objects::isNull)) {
            if (alternatives.stream().distinct().count() != alternatives.size()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Options must be unique!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Options cannot be null!");
        }

        this.alternativeQuestionRepository.save(newQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Alternative Question Successfully!");
    }
}
