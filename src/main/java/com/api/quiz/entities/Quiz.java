package com.api.quiz.entities;

import com.api.quiz.dtos.QuizDto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz {

    @ManyToOne
    private User user;

    @OneToMany (mappedBy = "quiz")
    List<DirectQuestion> directQuestionList = new ArrayList<>();

    @OneToMany (mappedBy = "quiz")
    List<AlternativeQuestion> alternativeQuestionsList = new ArrayList<>();

    @OneToMany (mappedBy = "quiz")
    List<TrueOrFalseQuestion> trueOrFalseQuestionList = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameQuiz;

    private String visibility;

    private String accessPassword;

    public Quiz(QuizDto quizDto) {
        this.nameQuiz = quizDto.nameQuiz();
        this.visibility = quizDto.visibility();
        this.accessPassword = quizDto.accessPassword();
    }

    public Quiz() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getNameQuiz() {
        return nameQuiz;
    }

    public void setNameQuiz(String nameQuiz) {
        this.nameQuiz = nameQuiz;
    }

    public String getAccessPassword() {
        return accessPassword;
    }

    public void setAccessPassword(String accessPassword) {
        this.accessPassword = accessPassword;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<DirectQuestion> getDirectQuestionList() {
        return directQuestionList;
    }

    public void setDirectQuestionList(List<DirectQuestion> directQuestionList) {
        this.directQuestionList = directQuestionList;
    }

    public List<AlternativeQuestion> getAlternativeQuestionsList() {
        return alternativeQuestionsList;
    }

    public void setAlternativeQuestionsList(List<AlternativeQuestion> alternativeQuestionsList) {
        this.alternativeQuestionsList = alternativeQuestionsList;
    }

    public List<TrueOrFalseQuestion> getTrueOrFalseQuestionList() {
        return trueOrFalseQuestionList;
    }

    public void setTrueOrFalseQuestionList(List<TrueOrFalseQuestion> trueOrFalseQuestionList) {
        this.trueOrFalseQuestionList = trueOrFalseQuestionList;
    }
}
