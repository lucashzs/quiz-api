package com.api.quiz.entities;

import com.api.quiz.dtos.RegisterUserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.*;

@Table(name = User.TABLE_NAME)
@Entity
public class User {
    public static final String TABLE_NAME = "users-quiz";

    @OneToMany(mappedBy = "user")
    private Set<Quiz> quizzes;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    @Size(min = 8)
    private String password;

    private String birthDate;

    public User(RegisterUserDto registerUserDto, String encryptPassword) {
        this.birthDate = registerUserDto.birthDate();
        this.email = registerUserDto.email();
        this.password = encryptPassword;
        this.fullName = registerUserDto.fullName();
        this.nickname = registerUserDto.nickname();
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String username) {
        this.fullName = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public static User fromEntity(User user) {
        return user;
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }
}