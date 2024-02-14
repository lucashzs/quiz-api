package com.api.quiz.dtos;

public record UserDto(String username, String nickname, String birthDate, String password, String confirmPassword) {
}
