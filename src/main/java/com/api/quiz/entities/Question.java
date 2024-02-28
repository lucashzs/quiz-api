package com.api.quiz.entities;

import com.api.quiz.dtos.QuestionDto;
import com.api.quiz.enums.QuestionType;
import jakarta.persistence.*;

@Entity
public class Question {

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    private String questionText;

    private String correctAnswer;

    public Question(QuestionDto questionDto, Quiz quiz) {
        this.questionText = questionDto.questionText();
        this.correctAnswer = questionDto.correctAnswer();
        this.questionType = questionDto.questionType();
        this.quiz = quiz;
    }

    public Question() {

    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public QuestionType setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
        return questionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
