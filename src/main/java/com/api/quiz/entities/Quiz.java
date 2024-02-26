package com.api.quiz.entities;


import com.api.quiz.dtos.QuestionDto;
import com.api.quiz.dtos.QuizDto;
import jakarta.persistence.*;


import java.util.List;

@Entity
public class Quiz {

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "quiz",fetch = FetchType.LAZY)
    private List<Question> questions;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
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

    public Quiz(QuestionDto questionDto) {
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}
