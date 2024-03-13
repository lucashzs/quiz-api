package com.api.quiz.repositories;

import com.api.quiz.entities.AlternativeQuestion;
import com.api.quiz.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlternativeQuestionRepository extends JpaRepository<AlternativeQuestion, Long> {
    List<AlternativeQuestion> findByQuizIdAndQuizUser (Long quizId, User user);
}
