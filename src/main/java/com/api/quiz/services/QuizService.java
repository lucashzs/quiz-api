package com.api.quiz.services;

import com.api.quiz.dtos.QuestionDto;
import com.api.quiz.dtos.QuizAnswerDto;
import com.api.quiz.dtos.QuizDto;
import com.api.quiz.dtos.QuizResponseDto;
import com.api.quiz.entities.Question;
import com.api.quiz.entities.Quiz;
import com.api.quiz.errors.exceptions.BadRequestException;
import com.api.quiz.errors.exceptions.NotFoundException;
import com.api.quiz.repositories.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public ResponseEntity<QuizResponseDto> checkQuizAnswers(QuizAnswerDto answer) {
        Quiz quiz = quizRepository.findById(answer.getQuizId())
                .orElseThrow(() -> new BadRequestException("Quiz not found!"));

        if (quiz.getVisibility().equalsIgnoreCase("private")) {
            if (answer.getAccessPassword().isEmpty()) {
                throw new BadRequestException("This Quiz is private, you need to enter a access password!");
            }
            if (!answer.getAccessPassword().equalsIgnoreCase(quiz.getAccessPassword())) {
                throw new BadRequestException("Incorrect access password!");
            }
        }

        List<QuestionDto> incorrectQuestions = new ArrayList<>();

        for (Question question : quiz.getQuestions()) {
            if (!question.getCorrectAnswer().equalsIgnoreCase(answer.getAnswers().get(question.getId()))) {
                QuestionDto questionDto = new QuestionDto(question, question.getId());
                incorrectQuestions.add(questionDto);
            }
        }
        QuizResponseDto response = new QuizResponseDto();

        if (incorrectQuestions.isEmpty()) {
            response.setMessage("Correct answers!");
        } else {
            response.setMessage("There are wrong answers!");
        }

        response.setIncorrectQuestion(incorrectQuestions);
        return ResponseEntity.ok(response);
    }
}
