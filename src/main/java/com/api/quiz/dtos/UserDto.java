package com.api.quiz.dtos;

import com.api.quiz.entities.User;

public record UserDto(String username, String nickname, String email, String password, String birthDate) {

    public UserDto(User user) {
        this(user.getUsername(), user.getNickname(), user.getEmail(), user.getPassword(), user.getBirthDate());
    }
}
