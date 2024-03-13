package com.api.quiz.entities;

import com.api.quiz.dtos.TrueOrFalseQuestionCreateDto;
import jakarta.persistence.*;

@Entity
public class TrueOrFalseQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private String questionText;

    private Boolean correctAnswer;

    public TrueOrFalseQuestion(TrueOrFalseQuestionCreateDto trueOrFalseQuestionCreateDto, Quiz quiz) {
        this.questionText = trueOrFalseQuestionCreateDto.getQuestionText();
        this.correctAnswer = trueOrFalseQuestionCreateDto.getCorrectAnswer();
        this.setQuiz(quiz);
    }

    public TrueOrFalseQuestion() {
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
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
