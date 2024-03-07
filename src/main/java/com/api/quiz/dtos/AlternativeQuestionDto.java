package com.api.quiz.dtos;

import java.util.List;

public class AlternativeQuestionDto extends DirectQuestionDto {

    private List<String> options;

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
    public AlternativeQuestionDto(Long id, List<String> options) {
        this.options = options;
    }
}
