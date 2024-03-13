package com.api.quiz.entities;

import com.api.quiz.dtos.DirectQuestionCreateDto;
import jakarta.persistence.*;

@Entity
public class DirectQuestion{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private String questionText;

    private String correctAnswer;

    public DirectQuestion(DirectQuestionCreateDto directQuestionCreateDto, Quiz quiz) {
        this.questionText = directQuestionCreateDto.getQuestionText();
        this.correctAnswer = directQuestionCreateDto.getCorrectAnswer();
        this.setQuiz(quiz);
    }

    public DirectQuestion() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
