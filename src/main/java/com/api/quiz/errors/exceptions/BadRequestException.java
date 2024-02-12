package com.api.quiz.errors.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
