package com.api.quiz.services;

import com.api.quiz.dtos.*;
import com.api.quiz.entities.Quiz;
import com.api.quiz.entities.Rank;
import com.api.quiz.errors.exceptions.BadRequestException;
import com.api.quiz.errors.exceptions.NotFoundException;
import com.api.quiz.repositories.*;
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
    private final DirectQuestionRepository directQuestionRepository;
    private final AlternativeQuestionRepository alternativeQuestionRepository;
    private final TrueOrFalseQuestionRepository trueOrFalseQuestionRepository;
    private final RankRepository rankRepository;


    public QuizService(QuizRepository quizRepository, AuthenticationService authenticationService, DirectQuestionRepository directQuestionRepository, AlternativeQuestionRepository alternativeQuestionRepository, TrueOrFalseQuestionRepository trueOrFalseQuestionRepository, RankRepository rankRepository) {
        this.quizRepository = quizRepository;
        this.authenticationService = authenticationService;
        this.directQuestionRepository = directQuestionRepository;
        this.alternativeQuestionRepository = alternativeQuestionRepository;
        this.trueOrFalseQuestionRepository = trueOrFalseQuestionRepository;
        this.rankRepository = rankRepository;
    }

    public Quiz findById(Long quiz) {
        return this.quizRepository.findById(quiz)
                .orElseThrow(() -> new NotFoundException("Quiz Not Found!"));
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

    public List<Object> getQuestions(Long id) {
        var currentUser = authenticationService.getCurrentUser();

        var directQuestions = directQuestionRepository.findByQuizIdAndQuizUser(id, currentUser).stream().map(DirectQuestionListDto::new).toList();
        var alternativeQuestions = alternativeQuestionRepository.findByQuizIdAndQuizUser(id, currentUser).stream().map(AlternativeQuestionListDto::new).toList();
        var trueOrFalseQuestions = trueOrFalseQuestionRepository.findByQuizIdAndQuizUser(id, currentUser).stream().map(TrueOrFalseQuestionListDto::new).toList();

        var questionsList = new ArrayList<>();
        questionsList.addAll(directQuestions);
        questionsList.addAll(alternativeQuestions);
        questionsList.addAll(trueOrFalseQuestions);

        return questionsList;
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

        int correctAnswers = 0;

        List<Object> incorrectQuestions = new ArrayList<>();

        // Direct Question Logic
        var directQuestions = quiz.getDirectQuestionList()
                .stream()
                .map(DirectQuestionListDto::new)
                .toList();

        for (DirectQuestionListDto directQuestionListDto : directQuestions) {
            String answerKey = "Direct-" + directQuestionListDto.id();
            if (!directQuestionListDto.correctAnswer().equalsIgnoreCase(answer.getAnswers().get(answerKey))) {
                incorrectQuestions.add(directQuestionListDto);
            } else {
                correctAnswers++;
            }
        }

        // Alternative Question Logic
        var alternativeQuestions = quiz.getAlternativeQuestionsList()
                .stream()
                .map(AlternativeQuestionListDto::new)
                .toList();

        for (AlternativeQuestionListDto alternativeQuestionListDto : alternativeQuestions) {
            String answerKey = "Alternative-" + alternativeQuestionListDto.id();
            if (!alternativeQuestionListDto.correctAnswer().equalsIgnoreCase(answer.getAnswers().get(answerKey))) {
                incorrectQuestions.add(alternativeQuestionListDto);
            } else {
                correctAnswers++;
            }
        }

        // True Or False Question Logic
        var trueOrFalseQuestions = quiz.getTrueOrFalseQuestionList()
                .stream()
                .map(TrueOrFalseQuestionListDto::new)
                .toList();

        for (TrueOrFalseQuestionListDto trueOrFalseQuestionListDto : trueOrFalseQuestions) {
            String answerKey = "TrueOrFalse-" + trueOrFalseQuestionListDto.id();
            boolean userAnswer = Boolean.parseBoolean(answer.getAnswers().get(answerKey));

            if (trueOrFalseQuestionListDto.correctAnswer() != userAnswer) {
                incorrectQuestions.add(trueOrFalseQuestionListDto);
            } else {
                correctAnswers++;
            }
        }

        QuizIncorrectResponseDto response = new QuizIncorrectResponseDto();

        if (incorrectQuestions.isEmpty()) {
            response.setMessage("Correct answers!");
        } else {
            response.setMessage("There are wrong answers!");
        }

        Rank userRank = new Rank();
        userRank.setUserName(answer.getUserName());
        userRank.setCorrectAnswers(correctAnswers);
        userRank.setQuiz(quiz);

        rankRepository.save(userRank);
        response.setIncorrectQuestion(incorrectQuestions);
        return ResponseEntity.ok(response);
    }

    public List<Rank> getRankingByQuiz(long quizId) {
        List<Rank> ranks = rankRepository.findByQuizId(quizId);

        ranks.sort((rank1, rank2) -> rank2.getCorrectAnswers() - rank1.getCorrectAnswers());

        return ranks;
    }
}
