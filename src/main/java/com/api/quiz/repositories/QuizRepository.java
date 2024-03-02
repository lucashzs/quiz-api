package com.api.quiz.repositories;

import com.api.quiz.entities.Quiz;
import com.api.quiz.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByNameQuiz(String nameQuiz);
}
