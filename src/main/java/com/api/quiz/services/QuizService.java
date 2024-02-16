package com.api.quiz.services;

import com.api.quiz.dtos.QuizDto;
import com.api.quiz.entities.Question;
import com.api.quiz.entities.Quiz;
import com.api.quiz.entities.User;
import com.api.quiz.errors.exceptions.BadRequestException;
import com.api.quiz.repositories.QuizRepository;
import com.api.quiz.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizService(QuizRepository quizRepository, UserRepository userRepository) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> createDirectAnswer(QuizDto quizDto) {
        var newQuiz = new Quiz(quizDto);
        Optional<User> user = this.userRepository.findById(quizDto.userId());

        if (newQuiz.getVisibility().equalsIgnoreCase("private")) {
            if (newQuiz.getAccessPassword().isEmpty()) {
                throw new BadRequestException("Access password is required for private quiz!");
            }
        }

        if (user.isEmpty()) {
            throw new BadRequestException("User doesn't exist!");
        }
        if (this.quizRepository.findByNameQuiz(quizDto.nameQuiz()).isPresent()) {
            throw new BadRequestException("Quiz already exists!");
        }

        List<Question> questions = quizDto.questions().stream().map(questionDto -> {
            Question question = new Question();
            question.setQuestionText(questionDto.questionText());
            question.setCorrectAnswer(questionDto.correctAnswer());
            return question;
        }).collect(Collectors.toList());

        newQuiz.setUser(user.get());
        newQuiz.setQuestions(questions);

        this.quizRepository.save(newQuiz);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Quiz Successfully!");
    }
}


