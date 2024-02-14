package com.api.quiz.entities;


import com.api.quiz.dtos.QuizDto;
import jakarta.persistence.*;


import java.util.List;
import java.util.Objects;

@Entity
public class Quiz {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "quiz_id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
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

    public Quiz(User user, Long id, String nameQuiz, String visibility, String accessPassword) {
        this.user = user;
        this.id = id;
        this.nameQuiz = nameQuiz;
        this.visibility = visibility;
        this.accessPassword = accessPassword;
    }
}
