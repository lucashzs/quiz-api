package com.api.quiz.enums;

public enum QuestionType {

    DIRECT_QUESTION("DIRECT_QUESTION"),
    ALTERNATIVE_QUESTION("ALTERNATIVE_QUESTION"),
    TRUE_OR_FALSE_QUESTION("TRUE_OR_FALSE_QUESTION");

    private final String typeQuestion;

    QuestionType(String typeQuestion) {
        this.typeQuestion = typeQuestion;
    }

    public String getTypeQuestion() {
        return typeQuestion;
    }
}
