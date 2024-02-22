package com.api.quiz.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDto(
        @NotBlank(message = "An username is required to register!") String fullName,
        @NotBlank(message = "An nickname is required to register!") String nickname,
        @Email(message = "Email address sent in an invalid format!")
        @NotBlank(message = "An email is required to register!") String email,
        @NotBlank(message = "An password is required to register!") String password,
        @NotBlank(message = "You need to confirm your password to register!") String confirmPassword,
        @NotBlank(message = "An birthDate is required to register!") String birthDate) {
}
