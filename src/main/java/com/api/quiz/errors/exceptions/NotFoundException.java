package com.api.quiz.errors.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException (String errorMessage){
        super(errorMessage);
    }
}
