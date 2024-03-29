package com.api.quiz.entities;

import com.api.quiz.dtos.AlternativeQuestionCreateDto;
import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("ALTERNATIVE_QUESTION")
public class AlternativeQuestion{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;


    private String questionText;

    private String correctAnswer;

    @ElementCollection
    private List<String> alternatives;

    public AlternativeQuestion(AlternativeQuestionCreateDto alternativeQuestionCreateDto, Quiz quiz) {
        this.alternatives = alternativeQuestionCreateDto.getAlternatives();
        this.correctAnswer = alternativeQuestionCreateDto.getCorrectAnswer();
        this.questionText = alternativeQuestionCreateDto.getQuestionText();
        this.setQuiz(quiz);
    }
    public AlternativeQuestion() {
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

    public List<String> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<String> options) {
        this.alternatives = options;
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

