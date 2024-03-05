package com.api.quiz.entities;

import com.api.quiz.dtos.AlternativeQuestionDto;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("ALTERNATIVE_QUESTION")
public class AlternativeQuestion extends Question{

    @ElementCollection
    private List<String> options;

    public AlternativeQuestion(AlternativeQuestionDto alternativeQuestionInputDto, Quiz quiz) {
        super(alternativeQuestionInputDto);
        this.options = alternativeQuestionInputDto.getOptions();
        this.setQuiz(quiz);
    }
    public AlternativeQuestion() {
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}

