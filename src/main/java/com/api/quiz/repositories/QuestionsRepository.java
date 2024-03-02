package com.api.quiz.repositories;

import com.api.quiz.entities.Question;
import com.api.quiz.entities.Quiz;
import com.api.quiz.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Question, Long>  {

    List<Question> findByQuizIdAndQuizUser (Long quizId, User user);
}
