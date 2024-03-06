package com.api.quiz.entities;

import com.api.quiz.dtos.QuestionOutputDto;
import com.api.quiz.dtos.QuestionInputDto;
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

    public Question(QuestionOutputDto questionOutputDto, Quiz quiz) {
        this.questionText = questionOutputDto.getQuestionText();
        this.correctAnswer = questionOutputDto.getCorrectAnswer();
        this.quiz = quiz;
    }

    public Question(QuestionInputDto questionInputDto) {
        this.questionText = questionInputDto.getQuestionText();
        this.correctAnswer = questionInputDto.getCorrectAnswer();
    }

    public Question() {

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
