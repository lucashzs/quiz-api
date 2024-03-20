package com.api.quiz.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class QuizAnswerDto {

    @NotNull (message = "UserName can not be null!")
    private String userName;

    @NotNull (message = "Answers can not be null!")
    private Map<String, String> answers;

    @NotNull (message = "Quiz id can not be null!")
    private Long quizId;

    @NotNull (message = "Access Password can not be null!")
    private String accessPassword;

    public String getAccessPassword() {
        return accessPassword;
    }

    public void setAccessPassword(String accessPassword) {
        this.accessPassword = accessPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
}
