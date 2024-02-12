package com.api.quiz.errors.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException (String errorMessage){
        super(errorMessage);
    }
}
