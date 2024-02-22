package com.api.quiz.dtos;

public record UserDto(String fullName, String nickname, String birthDate, String password, String confirmPassword) {
}
