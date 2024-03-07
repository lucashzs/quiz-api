package com.api.quiz.entities;

import com.api.quiz.dtos.QuestionDto;
import com.api.quiz.dtos.DirectQuestionDto;
import com.api.quiz.dtos.TrueOrFalseQuestionDto;
import jakarta.persistence.*;

@Entity
public class Question {

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    private String correctAnswer;

    public Question(QuestionDto questionDto, Quiz quiz) {
        this.questionText = questionDto.getQuestionText();
        this.correctAnswer = questionDto.getCorrectAnswer();
        this.quiz = quiz;
    }

    public Question(DirectQuestionDto directQuestionDto) {
        this.questionText = directQuestionDto.getQuestionText();
        this.correctAnswer = directQuestionDto.getCorrectAnswer();
    }

    public Question() {

    }

    public Question(TrueOrFalseQuestionDto trueOrFalseQuestionDto, Quiz quiz) {
    }

    public Question(DirectQuestionDto directQuestionDto, Quiz quiz) {
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
