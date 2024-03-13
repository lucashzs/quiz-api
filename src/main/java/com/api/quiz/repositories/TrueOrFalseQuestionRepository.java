package com.api.quiz.repositories;

import com.api.quiz.entities.TrueOrFalseQuestion;
import com.api.quiz.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrueOrFalseQuestionRepository extends JpaRepository <TrueOrFalseQuestion, Long > {
    List<TrueOrFalseQuestion> findByQuizIdAndQuizUser (Long quizId, User user);
}
