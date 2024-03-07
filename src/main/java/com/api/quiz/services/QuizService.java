package com.api.quiz.services;

import com.api.quiz.dtos.*;
import com.api.quiz.entities.Question;
import com.api.quiz.entities.Quiz;
import com.api.quiz.errors.exceptions.BadRequestException;
import com.api.quiz.errors.exceptions.NotFoundException;
import com.api.quiz.repositories.QuestionsRepository;
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
    private final QuestionsRepository questionsRepository;

    public QuizService(QuizRepository quizRepository, AuthenticationService authenticationService, QuestionsRepository questionsRepository) {
        this.quizRepository = quizRepository;
        this.authenticationService = authenticationService;
        this.questionsRepository = questionsRepository;
    }

    public Quiz findById(Long quiz) {
        return this.quizRepository.findById(quiz)
                .orElseThrow(() -> new NotFoundException("Quiz Not Found"));
    }

    public ResponseEntity<QuizResponseDto> getQuiz(Long id) {
        var quiz = quizRepository.findById(id)
                .map(QuizDto::new).orElseThrow(() -> new NotFoundException("Quiz Not Found"));
        QuizResponseDto quizResponseDto = new QuizResponseDto(quiz.nameQuiz(), quiz.visibility(), quiz.accessPassword(), getQuestions(id));

        return ResponseEntity.ok(quizResponseDto);
    }

    @Transactional
    public ResponseEntity<List<QuizDto>> getAllQuiz() {
        List<QuizDto> quizzes = quizRepository.findAll()
                .stream()
                .map(QuizDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(quizzes);
    }

    public List<QuestionDto> getQuestions(Long id) {
        var currentUser = authenticationService.getCurrentUser();
        var quizQuestions = questionsRepository.findByQuizIdAndQuizUser(id, currentUser);

        return quizQuestions.stream()
                .map(question -> new QuestionDto(question, id))
                .collect(Collectors.toList());
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

    public ResponseEntity<QuizIncorrectResponseDto> checkQuizAnswers(QuizAnswerDto answer) {
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
        QuizIncorrectResponseDto response = new QuizIncorrectResponseDto();

        if (incorrectQuestions.isEmpty()) {
            response.setMessage("Correct answers!");
        } else {
            response.setMessage("There are wrong answers!");
        }

        response.setIncorrectQuestion(incorrectQuestions);
        return ResponseEntity.ok(response);
    }
}
