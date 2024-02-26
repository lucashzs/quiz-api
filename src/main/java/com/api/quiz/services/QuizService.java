package com.api.quiz.services;

import com.api.quiz.dtos.QuizAnswerDto;
import com.api.quiz.dtos.QuizDto;
import com.api.quiz.entities.Question;
import com.api.quiz.entities.Quiz;
import com.api.quiz.errors.exceptions.BadRequestException;
import com.api.quiz.errors.exceptions.NotFoundException;
import com.api.quiz.repositories.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final AuthenticationService authenticationService;

    public QuizService(QuizRepository quizRepository, AuthenticationService authenticationService) {
        this.quizRepository = quizRepository;
        this.authenticationService = authenticationService;
    }

    public Quiz findById(Long quiz) {
        return this.quizRepository.findById(quiz)
                .orElseThrow(() -> new NotFoundException("Quiz Not Found"));
    }


    public ResponseEntity<QuizDto> getQuizById(Long id) {
        QuizDto quizDto =
                quizRepository.findById(id)
                        .map(QuizDto::new).orElseThrow(() -> new NotFoundException("Quiz Not Found"));
        return ResponseEntity.ok(quizDto);

    }

    @Transactional
    public ResponseEntity<List<QuizDto>> getAllQuiz() {
        List<QuizDto> quizzes = quizRepository.findAll()
                .stream()
                .map(QuizDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(quizzes);
    }

    public ResponseEntity<Object> createQuiz(QuizDto quizDto) {
        var currentUser = authenticationService.getCurrentUser();

        var newQuiz = new Quiz(quizDto);
        if (newQuiz.getVisibility().equalsIgnoreCase("private")) {
            if (newQuiz.getAccessPassword().isEmpty()) {
                throw new BadRequestException("Access password is required for private quiz!");
            }
        }
        if (this.quizRepository.findByNameQuiz(quizDto.nameQuiz()).isPresent()) {
            throw new BadRequestException("Quiz already exists!");
        }
        newQuiz.setUser(currentUser);

        this.quizRepository.save(newQuiz);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Quiz Successfully!");
    }


    public boolean checkQuizAnswers(QuizAnswerDto answer) {
        Quiz quiz = quizRepository.findById(answer.getQuizId())
                .orElseThrow(() -> new BadRequestException("Quiz not found"));

        boolean allAnswersCorrect = true;

        for (Question question : quiz.getQuestions()) {
            if (!question.getCorrectAnswer().equals(answer.getAnswers().get(question.getId()))) {
                allAnswersCorrect = false;
            }
        }
        return allAnswersCorrect;
    }
}
