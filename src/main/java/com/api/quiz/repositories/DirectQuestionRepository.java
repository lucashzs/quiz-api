package com.api.quiz.repositories;

import com.api.quiz.entities.User;
import com.api.quiz.entities.DirectQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectQuestionRepository extends JpaRepository<DirectQuestion, Long> {
    List<DirectQuestion> findByQuizIdAndQuizUser (Long quizId, User user);
}
